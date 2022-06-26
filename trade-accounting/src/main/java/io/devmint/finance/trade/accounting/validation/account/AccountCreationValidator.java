package io.devmint.finance.trade.accounting.validation.account;

import io.devmint.finance.trade.accounting.service.CreateAccountRequest;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.compose.ComposeValidator;
import io.devmint.finance.trade.accounting.validation.currency.CurrencyValidation;
import io.devmint.finance.trade.accounting.validation.field.FieldValidator;
import io.devmint.finance.trade.accounting.validation.trade.BrokerValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class AccountCreationValidator implements Validator<CreateAccountRequest> {
    
    List<Validator<CreateAccountRequest> > validations = Arrays.asList(
            FieldValidator.forField(CreateAccountRequest::getBroker,new BrokerValidation(() -> "broker")),
            FieldValidator.forField(CreateAccountRequest::getCurrency, new CurrencyValidation(() -> "currency"))
    );

    Validator<CreateAccountRequest> compositeValidator;

    public AccountCreationValidator() {
        this.compositeValidator = new ComposeValidator<>(this.validations);
    }
    @Override
    public Predicate<CreateAccountRequest> getCondition() {
        return this.compositeValidator.getCondition();
    }

    @Override
    public List<Validator<CreateAccountRequest>> getValidations() {
        return this.compositeValidator.getValidations();
    }

    @Override
    public String getErrorMsg(CreateAccountRequest createAccountRequest) {
        return this.compositeValidator.getErrorMsg(createAccountRequest);
    }
}
