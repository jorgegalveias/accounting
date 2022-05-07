package io.devmint.finance.rabbitmq;

import com.rabbitmq.client.Channel;
import io.devmint.finance.rabbitmq.model.TradeOperation;
import io.devmint.finance.rabbitmq.model.TradeOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class Broker {
    public static String ALL_TRADES_QUEUE="all_trades_queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(Broker.class);
    public static void createExchanges(Channel channel){
        Arrays.stream(TradeOperation.values()).forEach(tradeOperation -> {
            try {
                channel.exchangeDeclare(tradeOperation.getExchange(),"direct");
            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        });
    }
    public static void  createQueues(Channel channel) {

        try {
            channel.queueDeclare(ALL_TRADES_QUEUE, false, false, false, null);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        Arrays.stream(TradeOrigin.values()).forEach(tradeOrigin -> {

            try {
                channel.queueDeclare(tradeOrigin.name()+"_"+TradeOperation.NEW_TRADE.getExchange(), false, false, false, null);
                channel.queueBind(tradeOrigin.name()+"_"+TradeOperation.NEW_TRADE.getExchange(), TradeOperation.NEW_TRADE.getExchange(), tradeOrigin.name());

                channel.queueDeclare(tradeOrigin.name()+"_"+TradeOperation.UPDATE_TRADE.getExchange(), false, false, false, null);
                channel.queueBind(tradeOrigin.name()+"_"+TradeOperation.UPDATE_TRADE.getExchange(), TradeOperation.UPDATE_TRADE.getExchange(), tradeOrigin.name());

            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        });
    }
    public static void setup(Channel channel){
        createExchanges(channel);
        createQueues(channel);
    }
}
