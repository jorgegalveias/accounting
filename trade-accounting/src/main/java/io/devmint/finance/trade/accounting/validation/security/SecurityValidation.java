package io.devmint.finance.trade.accounting.validation.security;

import io.devmint.finance.trade.accounting.model.Security;
import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SecurityValidation implements Validator<Security>{

    public static final Predicate<Security> NAME = security -> !security.getName().isBlank();
    public static final Predicate<Security> ISIN = security -> !security.getIsin().isBlank();

    Supplier<String> fieldName;

    public SecurityValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.fieldName = fieldName;
    }

    @Override
    public Predicate<Security> getCondition() {
        return security -> NAME.and(ISIN).test(security);
    }

    @Override
    public String getErrorMsg(Security security) {
        return String.format("%s=(isin=%s,name=%s) is not valid",this.fieldName.get(),security.getIsin(),security.getName());
    }
}
