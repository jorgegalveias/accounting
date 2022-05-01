package io.devmint.finance.rabbitmq.model.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devmint.finance.rabbitmq.model.Trade;
import io.devmint.finance.rabbitmq.model.TradeOperation;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TradeGenerator {

    static List<String> origins = Stream.of("BEST","MASP").collect(Collectors.toList());
    static Random originsRandomFactor = new Random(1001L);

    private static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    private static  final Trade generateTrade(){

        return Trade.builder().timestamp(Instant.now())
                              .tradeOperation(TradeOperation.NEW_TRADE)
                              .origin(origins.get(originsRandomFactor.nextInt(origins.size())))
                              .build();
    }

    public static final byte[] generate() throws JsonProcessingException {
        return  mapper.writeValueAsBytes(generateTrade());
    }
}
