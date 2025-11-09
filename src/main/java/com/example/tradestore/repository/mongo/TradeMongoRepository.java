package com.example.tradestore.repository.mongo;

import com.example.tradestore.entity.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TradeMongoRepository extends MongoRepository<Trade, String> {
    Optional<Trade> findTopByTradeIdOrderByVersionDesc(String tradeId);
}
