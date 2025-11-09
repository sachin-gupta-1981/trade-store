# Trade Store – Run & Test Guide (Local Setup)

## 1. Prerequisites
- Java 17
- Maven 3.8+
- Docker Desktop (Windows/Mac) or Docker Engine (Linux)
- Optional: VS Code / IntelliJ IDEA

## 2. Start Infrastructure (Kafka, Zookeeper, Mongo)
```bash
docker compose up -d
```
Check with:
```bash
docker ps
```
You should see containers for `zookeeper`, `kafka`, and `mongo` running.

## 3. Build & Run Application
```bash
mvn clean package
mvn spring-boot:run
```
Application will start at [http://localhost:8080](http://localhost:8080).

## 4. Test Endpoints
### Create Trade (POST)
```
POST http://localhost:8080/api/trades
Content-Type: application/json

{
  "tradeId": "T1",
  "version": 1,
  "counterPartyId": "CP-1",
  "bookId": "B1",
  "maturityDate": "2025-12-31"
}
```

### Get Trades (GET)
```
GET http://localhost:8080/api/trades
```

## 5. Kafka Test
Publish message to `trades` topic:
```bash
kafka-console-producer --broker-list localhost:9092 --topic trades
>{"tradeId":"T10","version":1,"counterPartyId":"CP-1","bookId":"B1","maturityDate":"2025-12-31"}
```

## 6. Logging Verification
All logs include correlation id, for example:
```
2025-11-09 18:20:31 INFO  ... CID:3d5e2b12-... - Incoming request [POST] /api/trades
```

## 7. Shutdown
```bash
docker compose down
```
