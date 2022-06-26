package io.devmint.finance.trade.accounting.validation.field;

import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FieldValidator<T> implements Validator<T> {

    Predicate<T> condition;
    Function<T,String> errorMsg;

    public FieldValidator(Predicate<T> condition, Function<T,String> errorMsg){
        Objects.requireNonNull(condition);
        Objects.requireNonNull(errorMsg);
        this.condition = condition;
        this.errorMsg = errorMsg;
    }
    public FieldValidator(Predicate<T> condition, Supplier<String> fieldName){
        Objects.requireNonNull(condition);
        this.condition = condition;
        this.errorMsg = fieldValue -> genericErrorMsg(fieldName,()-> String.valueOf(fieldValue));
    }

    @Override
    public Predicate<T> getCondition() {
        return this.condition;
    }

    @Override
    public String getErrorMsg(T fieldValue) {
        return this.errorMsg.apply(fieldValue);
    }
    public static <TYPE,FIELD> Validator<TYPE> forField(Function<TYPE,FIELD> fieldValue, Validator<FIELD> validator) {
        return new FieldValidator<TYPE>(type -> validator.getCondition().test(fieldValue.apply(type)), type -> validator.getErrorMsg(fieldValue.apply(type)));
    }

}
