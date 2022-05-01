package io.devmint.finance.trade.accounting.validation.generic;

import com.google.type.Money;
import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MoneyValidator implements Validator<Money> {

    public static final Predicate<Money> CURRENCY_CODE_VALIDATION = money -> !money.getCurrencyCode().isBlank();

    public static final Predicate<Money> UNITS_VALIDATION = money -> money.getUnits() >= 0;

    Supplier<String> fieldName;

    public MoneyValidator(Supplier<String> fieldName){
        this.fieldName = fieldName;
    }

    @Override
    public Predicate<Money> getCondition() {
        return money -> CURRENCY_CODE_VALIDATION.and(UNITS_VALIDATION).test(money);
    }

    @Override
    public String getErrorMsg(Money money) {
        return String.format("%s=(%s,%d) is not valid",this.fieldName.get(),money.getCurrencyCode(),money.getUnits());
    }
}
