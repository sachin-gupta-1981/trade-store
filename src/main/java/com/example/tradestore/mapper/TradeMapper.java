package com.example.tradestore.mapper;

import com.example.tradestore.dto.TradeDto;
import com.example.tradestore.entity.Trade;

public class TradeMapper {

    public static Trade fromDto(TradeDto dto) {
        if (dto == null) return null;
        Trade t = new Trade();
        t.setTradeId(dto.getTradeId());
        t.setVersion(dto.getVersion());
        t.setCounterPartyId(dto.getCounterPartyId());
        t.setBookId(dto.getBookId());
        t.setMaturityDate(dto.getMaturityDate());
        return t;
    }
}
