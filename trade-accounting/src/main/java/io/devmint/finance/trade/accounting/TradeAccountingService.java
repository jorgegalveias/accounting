package io.devmint.finance.trade.accounting;

import com.google.protobuf.Empty;
import io.devmint.finance.trade.accounting.model.Asset;
import io.devmint.finance.trade.accounting.model.Security;
import io.devmint.finance.trade.accounting.service.AssetRequest;
import io.devmint.finance.trade.accounting.service.AssetResponse;
import io.devmint.finance.trade.accounting.service.RegisterTradeRequest;
import io.devmint.finance.trade.accounting.service.TradeAccountingServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeAccountingService extends TradeAccountingServiceGrpc.TradeAccountingServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeAccountingService.class);

    // Integration Test
    @Override
    public void getAssets(AssetRequest request, StreamObserver<AssetResponse> responseObserver) {

        LOGGER.info("Getting Assets for broker {}", request.getBroker());

        AssetResponse response = AssetResponse.newBuilder().addAssets(Asset.newBuilder().setAmount(100).setPrice((float) 1.23).setSecurity(Security.newBuilder().setName("EURUSD").build()).build()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        //super.getAssets(request, responseObserver);
    }

    @Override
    public void registerTrade(RegisterTradeRequest request, StreamObserver<Empty> responseObserver) {
        super.registerTrade(request, responseObserver);
    }
}
