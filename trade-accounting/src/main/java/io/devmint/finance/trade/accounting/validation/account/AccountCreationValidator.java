package io.devmint.finance.trade.accounting.validation.account;

import io.devmint.finance.trade.accounting.service.CreateAccountRequest;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.generic.GenericValidator;
import io.devmint.finance.trade.accounting.validation.generic.MoneyValidator;
import io.devmint.finance.trade.accounting.validation.trade.BrokerValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AccountCreationValidator implements Validator<CreateAccountRequest> {

    List<Validator<CreateAccountRequest>> validations = Arrays.asList(
            fromFieldValidator(CreateAccountRequest::getBroker,new BrokerValidation(() -> "broker")),
            fromFieldValidator(CreateAccountRequest::getCurrency, new GenericValidator<>(MoneyValidator.CURRENCY_CODE_VALIDATION,()-> "currency"))
    );

    Predicate<CreateAccountRequest> condition;

    public AccountCreationValidator() {
        this.condition = validations.stream()
                .map(Validator::getCondition)
                .reduce(x -> true, Predicate::and);
    }
    @Override
    public Predicate<CreateAccountRequest> getCondition() {
        return this.condition;
    }

    @Override
    public List<Validator<CreateAccountRequest>> getValidations() {
        return this.validations;
    }

    @Override
    public String getErrorMsg(CreateAccountRequest createAccountRequest) {
        return getErrors(createAccountRequest).stream().collect(Collectors.joining(". "));
    }
    static <CREATE_ACCOUNT,FIELD> Validator<CREATE_ACCOUNT> fromFieldValidator(Function<CREATE_ACCOUNT,FIELD> fieldValue, Validator<FIELD> validator) {
        return new GenericValidator<CREATE_ACCOUNT>(createAccount -> validator.getCondition().test(fieldValue.apply(createAccount)), createAccount -> validator.getErrorMsg(fieldValue.apply(createAccount)));
    }
}
