package com.example.tradestore.controller;

import com.example.tradestore.dto.TradeDto;
import com.example.tradestore.entity.Trade;
import com.example.tradestore.service.TradeService;
import com.example.tradestore.mapper.TradeMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class TradeControllerTest {

    @Test
    void createReturnsCreated() {
        TradeService svc = Mockito.mock(TradeService.class);
        TradeController controller = new TradeController(svc);

        TradeDto dto = new TradeDto();
        dto.setTradeId("T10");
        dto.setVersion(1);
        dto.setCounterPartyId("CP-1");
        dto.setBookId("B1");
        dto.setMaturityDate(LocalDate.now().plusDays(5));
        Trade t = TradeMapper.fromDto(dto);

        Mockito.when(svc.upsertTrade(any(Trade.class))).thenReturn(t);

        ResponseEntity<?> resp = controller.createOrUpdate(dto);
        assertEquals(201, resp.getStatusCodeValue());
    }
}
