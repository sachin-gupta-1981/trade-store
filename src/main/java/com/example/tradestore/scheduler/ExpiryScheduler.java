package com.example.tradestore.scheduler;

import com.example.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiryScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExpiryScheduler.class);
    private final TradeService service;

    public ExpiryScheduler(TradeService service) {
        this.service = service;
    }

    // Run daily at 00:05
    @Scheduled(cron = "0 5 0 * * ?")
    public void markExpired() {
        logger.info("Starting scheduled expiry check for trades");
        service.markExpiredTrades();
        logger.info("Completed scheduled expiry check for trades");
    }
}
