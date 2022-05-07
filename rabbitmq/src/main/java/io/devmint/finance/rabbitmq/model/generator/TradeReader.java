package io.devmint.finance.rabbitmq.model.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devmint.finance.rabbitmq.model.Trade;

import java.io.IOException;

public class TradeReader {

    private static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public static final Trade unmarshal(byte [] tradeBytes) throws IOException {
        return mapper.readValue(tradeBytes,Trade.class);
    }
    public static final byte[] marshal(Trade trade) throws JsonProcessingException {
        return mapper.writeValueAsBytes(trade);
    }
}
