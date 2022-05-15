package io.devmint.finance.trade.accounting.validation.account;

import io.devmint.finance.trade.accounting.service.GetAccountRequest;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.compose.ComposeValidator;
import io.devmint.finance.trade.accounting.validation.trade.BrokerValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class GetAccountValidator implements Validator<GetAccountRequest> {

    List<Validator<GetAccountRequest>> validators = Arrays.asList(
            ComposeValidator.fromFieldValidator(GetAccountRequest::getBroker,new BrokerValidation(() -> "broker"))
    );

    Validator<GetAccountRequest> compositeValidator;

    public GetAccountValidator(){
        this.compositeValidator = new ComposeValidator<GetAccountRequest>(this.validators);
    }
    @Override
    public Predicate<GetAccountRequest> getCondition() {
        return this.compositeValidator.getCondition();
    }
    @Override
    public List<Validator<GetAccountRequest>> getValidations() {
        return this.compositeValidator.getValidations();
    }

    @Override
    public String getErrorMsg(GetAccountRequest getAccountRequest) {
        return this.compositeValidator.getErrorMsg(getAccountRequest);
    }
}
