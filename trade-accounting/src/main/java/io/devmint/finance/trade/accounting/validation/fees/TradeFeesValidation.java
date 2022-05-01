package io.devmint.finance.trade.accounting.validation.fees;

import com.google.type.Money;
import io.devmint.finance.trade.accounting.validation.Validator;

import java.util.function.Predicate;

public class TradeFeesValidation implements Validator<Money> { // TODO> Test this class

    public static final Predicate<Money> CURRENCY_CODE_VALIDATION = money -> !money.getCurrencyCode().isBlank();
    public static final Predicate<Money> UNITS_VALIDATION = money -> money.getUnits() >= 0;


    @Override
    public Predicate<Money> getCondition() {
        return tradeFees -> CURRENCY_CODE_VALIDATION.and(UNITS_VALIDATION).test(tradeFees);
    }

    @Override
    public String getErrorMsg(Money money) {
        return null;
    }
}
