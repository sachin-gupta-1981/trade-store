package com.example.tradestore.repository;

import com.example.tradestore.entity.Trade;
import org.springframework.stereotype.Component;
import java.util.List;
import com.example.tradestore.repository.jpa.TradeRepository;
import com.example.tradestore.repository.mongo.TradeMongoRepository;
/**
 * A thin wrapper to persist to both SQL and Mongo stores for demonstration.
 */
@Component
public class DualTradeRepository {

    private final TradeRepository sqlRepo;
    private final TradeMongoRepository mongoRepo;

    public DualTradeRepository(TradeRepository sqlRepo, TradeMongoRepository mongoRepo) {
        this.sqlRepo = sqlRepo;
        this.mongoRepo = mongoRepo;
    }

    public Trade save(Trade trade) {
        // Persist to SQL (authoritative)
        var savedSql = sqlRepo.save(trade);
        // Map SQL id to mongo record for traceability
        savedSql.setSqlId(savedSql.getSqlId());
        // Ensure Mongo id is unset so Mongo generates its own
        savedSql.setId(null);
        mongoRepo.save(savedSql);
        return savedSql;
    }

    public List<Trade> findAllFromSql() {
        return sqlRepo.findAll();
    }
}
