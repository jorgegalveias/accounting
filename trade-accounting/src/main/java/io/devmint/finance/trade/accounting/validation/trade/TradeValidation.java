package io.devmint.finance.trade.accounting.validation.trade;

import io.devmint.finance.trade.accounting.model.Trade;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.currency.CurrencyValidation;
import io.devmint.finance.trade.accounting.validation.generic.GenericValidator;
import io.devmint.finance.trade.accounting.validation.generic.MoneyValidator;
import io.devmint.finance.trade.accounting.validation.security.SecurityValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TradeValidation implements Validator<Trade> {

    List<Validator<Trade>> validations = Arrays.asList(
            fromFieldValidator(Trade::getAmount, new AmountValidation(() -> "amount")),
            fromFieldValidator(Trade::getDate, new DateValidation(() -> "date")),
            fromFieldValidator(Trade::getPrice, new PriceValidation(() -> "price")),
            fromFieldValidator(Trade::getForexRate, new ForexRateValidation(() -> "forexRate")),
            fromFieldValidator(Trade::getCurrency, new CurrencyValidation(() -> "currency")),
            fromFieldValidator(Trade::getSecurity, new SecurityValidation(() -> "security")),
            fromFieldValidator(Trade::getBroker, new BrokerValidation(() -> "broker")),
            fromFieldValidator(Trade::getTrader, new TraderValidation(() -> "trader")),
            fromFieldValidator(Trade::getFees, new MoneyValidator(() -> "fees")));

    Predicate<Trade> condition;

    TradeValidation() {
        this.condition = validations.stream()
                .map(Validator::getCondition)
                .reduce(x -> true, Predicate::and);
    }

    @Override
    public List<Validator<Trade>> getValidations() {
        return this.validations;
    }

    @Override
    public Predicate<Trade> getCondition() {
        return this.condition;
    }

    @Override
    public String getErrorMsg(Trade trade) {
        return checkForErrors(trade).stream().collect(Collectors.joining("."));
    }

    static <TRADE,FIELD> Validator<TRADE> fromFieldValidator(Function<TRADE,FIELD> fieldValue, Validator<FIELD> validator) {
        return new GenericValidator<TRADE>(trade -> validator.getCondition().test(fieldValue.apply(trade)), trade -> validator.getErrorMsg(fieldValue.apply(trade)));
    }

}
