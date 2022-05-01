package io.devmint.finance.trade.accounting.validation.trade

import io.devmint.finance.trade.accounting.model.Trade

import spock.lang.Specification
import spock.lang.Unroll

class PriceValidationSpec extends Specification {

    @Unroll
    def "Price #tradePrice is valid ? #isTradePriceValid"(float tradePrice, boolean isTradePriceValid){
        when:
            boolean result = new PriceValidation({->"price"}).isValid(tradePrice)
        then:
            result == isTradePriceValid
        where:
            tradePrice || isTradePriceValid
            1.40       || true
            4500       || true
            0          || false
            -1         || false
    }
}
