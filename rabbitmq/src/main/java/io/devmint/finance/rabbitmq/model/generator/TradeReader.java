package io.devmint.finance.rabbitmq.model.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.devmint.finance.rabbitmq.model.Trade;

import java.io.IOException;

public class TradeReader {

    private static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public static final Trade read(byte [] tradeBytes) throws IOException {
        return mapper.readValue(tradeBytes,Trade.class);
    }
}
