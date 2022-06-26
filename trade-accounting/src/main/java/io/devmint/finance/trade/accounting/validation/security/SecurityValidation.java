package io.devmint.finance.trade.accounting.validation.security;

import io.devmint.finance.trade.accounting.model.Security;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.compose.ComposeValidator;
import io.devmint.finance.trade.accounting.validation.field.FieldValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SecurityValidation implements Validator<Security>{

    List<Validator<Security>> validators = Arrays.asList(
        new FieldValidator<Security>(security -> !security.getName().isBlank(), ()-> "name"),
        new FieldValidator<Security>(security -> !security.getIsin().isBlank(), ()-> "isin")
    );

    Supplier<String> fieldName;
    Validator<Security> compositeValidator;

    public SecurityValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.fieldName = fieldName;
        this.compositeValidator = new ComposeValidator<>(this.validators);
    }
    
    @Override
    public Predicate<Security> getCondition() {
        return this.compositeValidator.getCondition();
    }
    @Override
    public List<Validator<Security>> getValidations() {
        return this.compositeValidator.getValidations();
    }

    @Override
    public String getErrorMsg(Security security) {
        return String.format("%s=(isin=%s,name=%s) is not valid",this.fieldName.get(),security.getIsin(),security.getName());
    }
}
