package com.example.tradestore.dto;

import java.time.LocalDate;

public class TradeDto {
    private String tradeId;
    private int version;
    private String counterPartyId;
    private String bookId;
    private LocalDate maturityDate;

    public TradeDto(){}

    // getters and setters
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
}
