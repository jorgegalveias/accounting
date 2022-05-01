package io.devmint.finance.trade.accounting.validation.currency;

import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Currency;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CurrencyValidation implements Validator<String> {

    Supplier<String> fieldName;

    public CurrencyValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.fieldName = fieldName;
    }

    @Override
    public Predicate<String> getCondition() {
        return currencyCode -> Currency.getAvailableCurrencies().stream()
                .map(Currency::getCurrencyCode)
                .filter(it -> it.equals(currencyCode))
                .findFirst().isPresent();
    }

    @Override
    public String getErrorMsg(String currencyCode) {
        return genericErrorMsg(this.fieldName,()-> currencyCode);
    }
}
