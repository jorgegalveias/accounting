package io.devmint.finance.trade.accounting.validation.trade


import io.devmint.finance.trade.accounting.model.Trade
import io.devmint.finance.trade.accounting.validation.currency.CurrencyValidation
import spock.lang.Specification
import spock.lang.Unroll

class CurrencyValidationSpec extends Specification {

    @Unroll
    def "Currency = \"#currencyName\" is valid ? #isValidCurrencyName"(String currencyName, boolean isValidCurrencyName) {

        when:
            boolean result = new CurrencyValidation({->"currency"}).isValid(currencyName);
        then:
            result == isValidCurrencyName
        where:
            currencyName                    || isValidCurrencyName
            "EUR"                           || true
            ""                              || false
    }

}
