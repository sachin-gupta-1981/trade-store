package com.example.tradestore.service;

import com.example.tradestore.entity.Trade;
import com.example.tradestore.repository.jpa.TradeRepository;
import com.example.tradestore.repository.DualTradeRepository;
import com.example.tradestore.dto.TradeDto;
import com.example.tradestore.mapper.TradeMapper;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository repo;
    private final DualTradeRepository dualRepo;

    public TradeService(TradeRepository repo, DualTradeRepository dualRepo) {
        this.repo = repo;
        this.dualRepo = dualRepo;
    }

    @Transactional
    public Trade upsertTrade(Trade incoming) {
        if (incoming == null) {
            throw new TradeValidationException("Incoming trade cannot be null");
        }
        if (incoming.getMaturityDate() == null) {
            throw new TradeValidationException("Maturity date is required");
        }
        LocalDate today = LocalDate.now();
        if (incoming.getMaturityDate().isBefore(today)) {
            throw new TradeValidationException("Maturity date is before today");
        }

        Optional<Trade> highest = repo.findTopByTradeIdOrderByVersionDesc(incoming.getTradeId());
        if (highest.isPresent()) {
            int highestVersion = highest.get().getVersion();
            if (incoming.getVersion() < highestVersion) {
                throw new TradeValidationException("Incoming version " + incoming.getVersion()
                        + " is lower than existing highest version " + highestVersion);
            }

            if (incoming.getVersion() == highestVersion) {
                Optional<Trade> existing = repo.findByTradeIdAndVersion(incoming.getTradeId(), incoming.getVersion());
                existing.ifPresent(e -> incoming.setSqlId(e.getSqlId()));
            }
        }

        incoming.setCreatedDate(LocalDate.now());
        incoming.setExpired(false);
        // persist in both stores (SQL + Mongo)
        return dualRepo.save(incoming);
    }

    @Transactional
    public Trade upsertFromDto(TradeDto dto) {
        Trade t = TradeMapper.fromDto(dto);
        return upsertTrade(t);
    }

    @Transactional
    public void markExpiredTrades() {
        LocalDate today = LocalDate.now();
        var list = repo.findByExpiredFalse();
        list.stream()
            .filter(t -> t.getMaturityDate() != null && t.getMaturityDate().isBefore(today))
            .forEach(t -> t.setExpired(true));
        repo.saveAll(list);
    }

    public List<Trade> getAllTrades() {
        return repo.findAll();
    }
}
