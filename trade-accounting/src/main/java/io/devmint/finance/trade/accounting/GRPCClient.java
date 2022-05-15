package io.devmint.finance.trade.accounting;

import io.devmint.finance.trade.accounting.model.Account;
import io.devmint.finance.trade.accounting.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GRPCClient {

    public static final Logger LOGGER = LoggerFactory.getLogger(GRPCClient.class);
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 7778).maxInboundMessageSize(104857600).usePlaintext().build();

        AccountServiceGrpc.AccountServiceBlockingStub syncClient = AccountServiceGrpc.newBlockingStub(channel);

        AssetRequest request = AssetRequest.newBuilder().setBroker("Dummy Broker").build();

        CreateAccountRequest request2 = CreateAccountRequest.newBuilder().setBroker("OANDA").setCurrency("EUR").build();

        syncClient.createAccount(request2);

        Account account = syncClient.getAccount(GetAccountRequest.newBuilder().setBroker("OANDA").build());

        LOGGER.info("{}",account);
        //AssetResponse response = syncClient.getAssets(request);

        //System.out.println(response.getAssetsList());

    }
}
