package com.example.tradestore.repository.jpa;

import com.example.tradestore.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    Optional<Trade> findTopByTradeIdOrderByVersionDesc(String tradeId);
    Optional<Trade> findByTradeIdAndVersion(String tradeId, int version);
    List<Trade> findByExpiredFalse();
}
