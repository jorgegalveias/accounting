package io.devmint.finance.trade.accounting;

import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import com.google.rpc.Code;
import com.google.rpc.Status;
import io.devmint.finance.trade.accounting.model.Asset;
import io.devmint.finance.trade.accounting.model.Security;
import io.devmint.finance.trade.accounting.service.*;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.account.AccountCreationValidator;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeAccountingService extends TradeAccountingServiceGrpc.TradeAccountingServiceImplBase {

    Validator<CreateAccountRequest> accountRequestValidator = new AccountCreationValidator();

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
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountResponse> responseObserver) {

        if(accountRequestValidator.hasErrors(request)){
            Status status = Status.newBuilder()
                    .setCode(Code.INVALID_ARGUMENT.getNumber())
                    .setMessage(accountRequestValidator.getErrorMsg(request))
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        super.createAccount(request, responseObserver);
    }
}
