package com.example.tradestore.controller;

import com.example.tradestore.dto.TradeDto;
import com.example.tradestore.entity.Trade;
import com.example.tradestore.service.TradeService;
import com.example.tradestore.service.TradeValidationException;
import com.example.tradestore.mapper.TradeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final TradeService service;

    public TradeController(TradeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdate(@RequestBody TradeDto tradeDto) {
        try {
            Trade t = TradeMapper.fromDto(tradeDto);
            Trade saved = service.upsertTrade(t);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (TradeValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        }
    }

    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        return ResponseEntity.ok(service.getAllTrades());
    }
}
