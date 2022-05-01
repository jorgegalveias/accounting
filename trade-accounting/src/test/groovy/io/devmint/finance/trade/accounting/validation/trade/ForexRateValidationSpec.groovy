package io.devmint.finance.trade.accounting.validation.trade

import io.devmint.finance.trade.accounting.model.Trade

import spock.lang.Specification
import spock.lang.Unroll

class ForexRateValidationSpec extends Specification {

    @Unroll
    def "Price #tradeForexRate is valid ? #isTradeForexRateValid"(float tradeForexRate, boolean isTradeForexRateValid){
        when:
            boolean result = new ForexRateValidation({-> "forexRate"}).isValid(tradeForexRate);
        then:
            result == isTradeForexRateValid
        where:
            tradeForexRate || isTradeForexRateValid
            1.40           || true
            4500           || true
            0              || false
            -1             || false
    }
}
