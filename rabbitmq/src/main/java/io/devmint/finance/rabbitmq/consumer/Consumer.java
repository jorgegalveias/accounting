package io.devmint.finance.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.devmint.finance.rabbitmq.model.Trade;
import io.devmint.finance.rabbitmq.model.generator.TradeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class Consumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    public static final String QUEUE_NAME = "my-queue";

    public static final Integer NUMBER_OF_THREADS = 55;

    public static final Integer WORKER_PROCESSING_TIME_IN_MILLI = 200;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Trade receivedTrade = TradeReader.read(delivery.getBody());
            LOGGER.info("Received Trade {}",receivedTrade);
            try {
                TimeUnit.MILLISECONDS.sleep(WORKER_PROCESSING_TIME_IN_MILLI);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        };

        Runnable worker = () -> {
            try {
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

            } catch (IOException e) {
                LOGGER.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        };


        ForkJoinPool pool = ForkJoinPool.commonPool();

        IntStream.range(0,NUMBER_OF_THREADS).forEach(threadNumber -> pool.submit(worker));

        pool.awaitTermination(110, TimeUnit.MINUTES);

    }
}
