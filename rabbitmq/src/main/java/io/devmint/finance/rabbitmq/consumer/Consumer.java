package io.devmint.finance.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.devmint.finance.rabbitmq.Broker;
import io.devmint.finance.rabbitmq.model.Trade;
import io.devmint.finance.rabbitmq.model.TradeOperation;
import io.devmint.finance.rabbitmq.model.TradeOrigin;
import io.devmint.finance.rabbitmq.model.generator.TradeReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Consumer {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    public static final String QUEUE_NAME = "my-queue";

    public static final Integer NUMBER_OF_THREADS = 150;


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Broker.createExchanges(channel);
        Broker.createQueues(channel);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /*DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Trade receivedTrade = TradeReader.unmarshal(delivery.getBody());
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
        };*/


        ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        //pool = ForkJoinPool.commonPool();

        IntStream.range(1, NUMBER_OF_THREADS).boxed().map(it -> new Worker(channel, Broker.ALL_TRADES_QUEUE))
                .forEach(worker -> pool.submit(worker));

        pool.awaitTermination(110, TimeUnit.MINUTES);

    }

}
