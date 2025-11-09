package com.example.tradestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.tradestore.repository.jpa")
@EnableMongoRepositories(basePackages = "com.example.tradestore.repository.mongo")
public class TradeStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeStoreApplication.class, args);
    }
}
