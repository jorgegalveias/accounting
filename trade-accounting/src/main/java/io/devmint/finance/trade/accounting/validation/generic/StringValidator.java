package io.devmint.finance.trade.accounting.validation.generic;

import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class StringValidator implements Validator<String> {

    //GenericValidator<String> validator;

    Supplier<String> fieldName;

    public StringValidator(Supplier<String> fieldName){
        this.fieldName = fieldName;
      //  this.validator = new GenericValidator<>(this.getCondition(),this::getErrorMsg);
    }
    @Override
    public Predicate<String> getCondition() {
        return value -> Objects.nonNull(value) && !value.isBlank();
    }

    @Override
    public String getErrorMsg(String fieldValue) {
        return genericErrorMsg(this.fieldName,() -> fieldValue);
    }
}
