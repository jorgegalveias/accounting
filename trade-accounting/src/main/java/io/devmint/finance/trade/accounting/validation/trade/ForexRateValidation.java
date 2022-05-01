package io.devmint.finance.trade.accounting.validation.trade;

import io.devmint.finance.trade.accounting.model.Trade;
import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ForexRateValidation implements Validator<Float> {

    Supplier<String> fieldName;

    public ForexRateValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.fieldName = fieldName;
    }

    @Override
    public Predicate<Float> getCondition() {
        return value -> value > 0.0;
    }

    @Override
    public String getErrorMsg(Float forexRate) {
        return genericErrorMsg(fieldName,()->String.valueOf(forexRate));
    }

}
