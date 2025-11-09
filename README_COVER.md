Trade Store - Cover Document
============================

This document briefly explains the code layout, key files, and how the solution addresses standard banking requirements such as auditability, validation, secure defaults and operational concerns.

Project layout (important files)
- pom.xml
- src/main/java/com/example/tradestore
  - TradeStoreApplication.java        : Spring Boot bootstrap with scheduling enabled.
  - entity/Trade.java                 : Domain model. Contains audit fields: createdDate, expired flag.
  - repository/TradeRepository.java   : SQL JPA repository (primary persistence).
  - repository/TradeMongoRepository.java : Mongo repository for document store backup/analytics.
  - repository/DualTradeRepository.java  : Demonstration wrapper to persist to both SQL and Mongo.
  - service/TradeService.java         : Business logic implementing rules:
        * Reject maturity before today
        * Reject lower version
        * Replace same version
        * Mark expired on schedule
  - controller/TradeController.java   : REST entry points (POST /api/trades, GET /api/trades)
  - api/GlobalExceptionHandler.java   : Centralised API error model and mapping.
  - api/ApiError.java                 : Standard API error payload with correlation id.
  - filter/CorrelationIdFilter.java   : Populates `X-Correlation-Id` into MDC for logs & tracing.
  - streaming/TradeKafkaListener.java : Kafka listener demonstration; consumes JSON trade messages.
  - mapper/TradeMapper.java           : DTO to entity mapping used by streaming and REST paths.
  - dto/TradeDto.java                 : Lightweight DTO for external payloads.

- Validation: All domain rules enforced at service layer (fail-fast) to ensure consistency.
- Audit fields: Trades include createdDate and expired flag for downstream auditing.
- Correlation: All API requests are assigned/propagated with X-Correlation-Id and logged via MDC.
- Dual persistence: Demonstrates storing in SQL (authoritative) and Mongo (analytics/replica) for resilience and analytics.
- Exception handling: Centralised and explicit mapping of validation and generic errors with consistent payload.
- CI & security: GitHub Actions pipeline runs unit tests and OWASP dependency-check scan; threshold configurable to bank policy.
- Operational: Scheduler to mark expiry and H2 console available for local troubleshooting.

How to run locally (summary)
1. Java 17 + Maven installed.
2. mvn clean package
3. mvn spring-boot:run
4. POST /api/trades to create trades, GET /api/trades to view.
