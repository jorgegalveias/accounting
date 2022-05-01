package io.devmint.finance.trade.accounting.validation.trade;

import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.generic.StringValidator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BrokerValidation implements Validator<String> {

    private StringValidator stringValidator;

    public BrokerValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.stringValidator = new StringValidator(fieldName);
    }
    @Override
    public Predicate<String> getCondition() {
        return this.stringValidator.getCondition();
    }

    @Override
    public String getErrorMsg(String s) {
        return this.stringValidator.getErrorMsg(s);
    }
}
