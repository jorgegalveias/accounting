package io.devmint.finance.trade.accounting;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GRPCServer {

    private static final int DEFAULT_GRPC_SERVER_PORT = 7778;
    private static final Logger LOGGER = LoggerFactory.getLogger(GRPCServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        Integer grpcPort = Integer.valueOf(System.getenv().getOrDefault(AppEnvVars.GRPC_SERVER_PORT,String.valueOf(DEFAULT_GRPC_SERVER_PORT)));

        Server server = ServerBuilder.forPort(grpcPort)
                .addService(new TradeAccountingService())
                .build();

        LOGGER.info("Binding grpc to port {}", grpcPort);

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("GRPC Server was terminated");
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
