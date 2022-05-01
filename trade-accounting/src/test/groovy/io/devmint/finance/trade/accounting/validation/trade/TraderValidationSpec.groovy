package io.devmint.finance.trade.accounting.validation.trade


import spock.lang.Specification
import spock.lang.Unroll

class TraderValidationSpec extends Specification {

    @Unroll
    def "Trader = \"#traderName\" is valid ? #isValidTraderName"(String traderName, boolean isValidTraderName) {

        when:
            boolean result = new TraderValidation({->"trader"}).isValid(traderName);
        then:
            result == isValidTraderName
        where:
            traderName                    || isValidTraderName
            "The Trader"                  || true
            ""                            || false
            "The Trader with spaces   "   || true
    }
}
