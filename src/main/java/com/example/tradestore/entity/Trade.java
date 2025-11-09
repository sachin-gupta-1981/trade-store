package com.example.tradestore.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "trades", uniqueConstraints = @UniqueConstraint(columnNames = {"tradeId", "version"}))
@Document(collection = "trades")
public class Trade {

    // JPA ID (primary key in SQL)
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sqlId;

    // Mongo ID (excluded from JPA persistence)
    @org.springframework.data.annotation.Id
    @Transient
    private String mongoId;


    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;
    private LocalDate createdDate;
    private boolean expired;

    public Trade(){}

    public Trade(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = LocalDate.now();
        this.expired = false;
    }

    // getters and setters

    public String getId() { return mongoId; }
    public void setId(String id) { this.mongoId = id; }

    public Long getSqlId() { return sqlId; }
    public void setSqlId(Long sqlId) { this.sqlId = sqlId; }

    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public String getCounterPartyId() { return counterPartyId; }
    public void setCounterPartyId(String counterPartyId) { this.counterPartyId = counterPartyId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public boolean isExpired() { return expired; }
    public void setExpired(boolean expired) { this.expired = expired; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return version == trade.version && Objects.equals(tradeId, trade.tradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeId, version);
    }
}
