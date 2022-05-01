package io.devmint.finance.trade.accounting;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GRPCServer {

    private static final int PORT = 7778;
    private static final Logger LOGGER = LoggerFactory.getLogger(GRPCServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(PORT)
                .addService(new TradeAccountingService())
                .build();

        LOGGER.info("Binding port {}",PORT);

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("GRPC Server was terminated");
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
