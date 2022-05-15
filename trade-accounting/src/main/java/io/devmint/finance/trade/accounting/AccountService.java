package io.devmint.finance.trade.accounting;

import io.devmint.finance.trade.accounting.dao.AccountDAO;
import io.devmint.finance.trade.accounting.model.Account;
import io.devmint.finance.trade.accounting.model.Asset;
import io.devmint.finance.trade.accounting.model.Security;
import io.devmint.finance.trade.accounting.service.*;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.account.AccountCreationValidator;
import io.devmint.finance.trade.accounting.validation.account.GetAccountValidator;
import io.devmint.finance.trade.accounting.validation.generic.StringValidator;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class AccountService extends  AccountServiceGrpc.AccountServiceImplBase{

    Validator<CreateAccountRequest> accountRequestValidator = new AccountCreationValidator();
    Validator<GetAccountRequest> getAccountRequestValidator = new GetAccountValidator();

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private AccountDAO accountDAO = new AccountDAO();

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountResponse> responseObserver) {

        if(accountRequestValidator.hasErrors(request)){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(accountRequestValidator.getErrorMsg(request)).asRuntimeException());
            return;
        }

        String accountId = buildAccountId(request.getAccountId(),request.getBroker());

        Account account = Account.newBuilder()
                .setId(accountId)
                .setBroker(request.getBroker())
                .setCurrency(request.getCurrency()).build();

        this.accountDAO.write(account);

        responseObserver.onNext(CreateAccountResponse.newBuilder().setAccountId(request.getAccountId()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAccount(GetAccountRequest request, StreamObserver<Account> responseObserver) {

        if(getAccountRequestValidator.hasErrors(request)){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(getAccountRequestValidator.getErrorMsg(request)).asRuntimeException());
            return;
        }

        String accountId = buildAccountId(request.getAccountId(), request.getBroker());

        Account account = this.accountDAO.read(request.getBroker(), accountId);

        responseObserver.onNext(account);

        responseObserver.onCompleted();
    }
    public String buildAccountId(String accountId,String brokerId){

        Predicate<String> basicStringValidation = new StringValidator(() -> "accountId").getCondition();

        return Stream.of(accountId)
                .filter(basicStringValidation)
                .findFirst()
                .orElse(brokerId);
    }
}
