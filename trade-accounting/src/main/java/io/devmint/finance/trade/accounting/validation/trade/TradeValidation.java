package io.devmint.finance.trade.accounting.validation.trade;

import io.devmint.finance.trade.accounting.model.Trade;
import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.compose.ComposeValidator;
import io.devmint.finance.trade.accounting.validation.currency.CurrencyValidation;
import io.devmint.finance.trade.accounting.validation.field.FieldValidator;
import io.devmint.finance.trade.accounting.validation.generic.MoneyValidator;
import io.devmint.finance.trade.accounting.validation.security.SecurityValidation;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TradeValidation implements Validator<Trade> {

    List<Validator<Trade>> validators = Arrays.asList(
            FieldValidator.forField(Trade::getAmount, new AmountValidation(() -> "amount")),
            FieldValidator.forField(Trade::getDate, new DateValidation(() -> "date")),
            FieldValidator.forField(Trade::getPrice, new PriceValidation(() -> "price")),
            FieldValidator.forField(Trade::getForexRate, new ForexRateValidation(() -> "forexRate")),
            FieldValidator.forField(Trade::getCurrency, new CurrencyValidation(() -> "currency")),
            FieldValidator.forField(Trade::getSecurity, new SecurityValidation(() -> "security")),
            FieldValidator.forField(Trade::getBroker, new BrokerValidation(() -> "broker")),
            FieldValidator.forField(Trade::getTrader, new TraderValidation(() -> "trader")),
            FieldValidator.forField(Trade::getFees, new MoneyValidator(() -> "fees")));


    Validator<Trade> compositeValidator;

    TradeValidation() {
        this.compositeValidator = new ComposeValidator<>(this.validators);
    }

    @Override
    public List<Validator<Trade>> getValidations() {
        return this.compositeValidator.getValidations();
    }

    @Override
    public Predicate<Trade> getCondition() {
        return this.compositeValidator.getCondition();
    }

    @Override
    public String getErrorMsg(Trade trade) {
        return this.compositeValidator.getErrorMsg(trade);
    }


}
