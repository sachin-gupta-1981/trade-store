package com.example.tradestore.streaming;

import com.example.tradestore.dto.TradeDto;
import com.example.tradestore.service.TradeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Kafka listener that accepts trade messages (JSON) and delegates to service.
 */
@Component
public class TradeKafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(TradeKafkaListener.class);
    private final TradeService tradeService;
    private final ObjectMapper mapper = new ObjectMapper();

    public TradeKafkaListener(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @KafkaListener(topics = "trades", groupId = "trade-store-group")
    public void consume(String message) {
        try {
            logger.info("Received Kafka message: {}", message);
            TradeDto dto = mapper.readValue(message, TradeDto.class);
            tradeService.upsertFromDto(dto);
        } catch (Exception ex) {
            logger.error("Failed to process kafka message", ex);
        }
    }
}
