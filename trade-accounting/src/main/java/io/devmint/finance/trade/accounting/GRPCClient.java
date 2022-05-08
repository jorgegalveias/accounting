package io.devmint.finance.trade.accounting;

import io.devmint.finance.trade.accounting.service.AssetRequest;
import io.devmint.finance.trade.accounting.service.AssetResponse;
import io.devmint.finance.trade.accounting.service.CreateAccountRequest;
import io.devmint.finance.trade.accounting.service.TradeAccountingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GRPCClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 7778).maxInboundMessageSize(104857600).usePlaintext().build();

        TradeAccountingServiceGrpc.TradeAccountingServiceBlockingStub syncClient = TradeAccountingServiceGrpc.newBlockingStub(channel);

        AssetRequest request = AssetRequest.newBuilder().setBroker("Dummy Broker").build();

        CreateAccountRequest request2 = CreateAccountRequest.newBuilder().build();

        syncClient.createAccount(request2);

        AssetResponse response = syncClient.getAssets(request);

        System.out.println(response.getAssetsList());

    }
}
