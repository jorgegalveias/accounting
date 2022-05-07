package io.devmint.finance.rabbitmq.model.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.devmint.finance.rabbitmq.model.Trade;
import io.devmint.finance.rabbitmq.model.TradeOperation;
import io.devmint.finance.rabbitmq.model.TradeOrigin;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TradeGenerator {


    // 95 % of the trades are NEW TRADES
    // 5 % of the trades are UPDATE TRADES

    static List<String> origins = Stream.of(TradeOrigin.values()).map(TradeOrigin::name).collect(Collectors.toList());
    static Random originsRandomFactor = new Random(1001L);

    static Function<Integer,TradeOperation> tradeOperationGenerator = number -> {
        if(number > 3){
            return TradeOperation.NEW_TRADE;
        }
        return TradeOperation.UPDATE_TRADE;
    };

    private static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    public static  final Trade generateTrade(){

        TradeOperation tradeType = tradeOperationGenerator.apply(originsRandomFactor.nextInt(100));

        return Trade.builder().timestamp(Instant.now())
                              .uuid(UUID.randomUUID())
                              .tradeOperation(tradeType)
                              .origin(origins.get(originsRandomFactor.nextInt(origins.size())))
                              .build();
    }

    public static final byte[] generate() throws JsonProcessingException {
        return TradeReader.marshal(generateTrade());
    }
}
