package io.devmint.finance.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.devmint.finance.rabbitmq.model.generator.TradeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class Producer {

    public static final String QUEUE_NAME = "my-queue";
    public static final Integer NUMBER_OF_MESSAGES = 1000 * 1000 * 50 ;
    public static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) throws IOException, TimeoutException {

        Instant start = Instant.now();
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            LOGGER.info("Connection ID {}", channel.getConnection());

            IntStream.range(0, NUMBER_OF_MESSAGES).boxed().parallel().map(number -> {
                try {
                    return TradeGenerator.generate();
                } catch (JsonProcessingException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }).forEach(trade ->
            {
                try {
                    channel.basicPublish("", QUEUE_NAME, null, trade);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            });
        }
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        LOGGER.info("It took {} seconds to produce {} trades",duration.getSeconds(),NUMBER_OF_MESSAGES);
    }
}
