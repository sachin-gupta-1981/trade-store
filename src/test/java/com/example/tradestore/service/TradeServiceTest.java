package com.example.tradestore.service;

import com.example.tradestore.entity.Trade;
import com.example.tradestore.repository.jpa.TradeRepository;
import com.example.tradestore.repository.DualTradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TradeServiceTest {

    private TradeRepository repo;
    private DualTradeRepository dualRepo;
    private TradeService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(TradeRepository.class);
        dualRepo = Mockito.mock(DualTradeRepository.class);
        service = new TradeService(repo, dualRepo);
    }

    @Test
    void rejectsTradeWithPastMaturity() {
        Trade t = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1));
        TradeValidationException ex = assertThrows(TradeValidationException.class, () -> service.upsertTrade(t));
        assertTrue(ex.getMessage().contains("Maturity date"));
    }

    @Test
    void rejectsLowerVersion() {
        Trade incoming = new Trade("T2", 1, "CP-2", "B1", LocalDate.now().plusDays(10));
        Trade existing = new Trade("T2", 2, "CP-2", "B1", LocalDate.now().plusDays(10));
        when(repo.findTopByTradeIdOrderByVersionDesc("T2")).thenReturn(Optional.of(existing));

        TradeValidationException ex = assertThrows(TradeValidationException.class, () -> service.upsertTrade(incoming));
        assertTrue(ex.getMessage().contains("lower than existing highest version"));
    }

    @Test
    void replacesWhenSameVersion() {
        Trade incoming = new Trade("T3", 2, "CP-3", "B1", LocalDate.now().plusDays(10));
        Trade existing = new Trade("T3", 2, "CP-3", "B1", LocalDate.now().plusDays(9));
        existing.setSqlId(100L);
        when(repo.findTopByTradeIdOrderByVersionDesc("T3")).thenReturn(Optional.of(existing));
        when(repo.findByTradeIdAndVersion("T3", 2)).thenReturn(Optional.of(existing));
        when(dualRepo.save(Mockito.any(Trade.class))).thenAnswer(i -> i.getArgument(0));

        Trade saved = service.upsertTrade(incoming);
        assertNotNull(saved);
        assertEquals(2, saved.getVersion());
        assertEquals("T3", saved.getTradeId());
    }
}
