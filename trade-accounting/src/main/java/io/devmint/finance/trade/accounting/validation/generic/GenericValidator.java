package io.devmint.finance.trade.accounting.validation.generic;

import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericValidator<T> implements Validator<T> {

    Predicate<T> condition;
    Function<T,String> errorMsg;

    public GenericValidator(Predicate<T> condition,Function<T,String> errorMsg){
        Objects.requireNonNull(condition);
        Objects.requireNonNull(errorMsg);
        this.condition = condition;
        this.errorMsg = errorMsg;
    }

    @Override
    public Predicate<T> getCondition() {
        return this.condition;
    }

    @Override
    public String getErrorMsg(T t) {
        return this.errorMsg.apply(t);
    }

}
