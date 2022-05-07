package io.devmint.finance.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import io.devmint.finance.rabbitmq.model.Trade;
import io.devmint.finance.rabbitmq.model.generator.TradeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Worker implements Runnable{

    private final String queueName;
    private Channel channel;
    public static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static final Integer WORKER_PROCESSING_TIME_IN_MILLI = 200;
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        Trade receivedTrade = TradeReader.unmarshal(delivery.getBody());
        //LOGGER.info("Received Trade {}",receivedTrade);
        /*try {
            TimeUnit.MILLISECONDS.sleep(WORKER_PROCESSING_TIME_IN_MILLI);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }*/
    };

    public Worker(Channel channel, String queueName){
        Objects.requireNonNull(channel,queueName);
        this.channel = channel;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(this.queueName, true, deliverCallback, consumerTag -> { });

        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
