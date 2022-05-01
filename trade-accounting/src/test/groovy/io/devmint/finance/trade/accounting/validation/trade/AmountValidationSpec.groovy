package io.devmint.finance.trade.accounting.validation.trade

import com.google.protobuf.Timestamp
import io.devmint.finance.trade.accounting.model.Trade
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

class AmountValidationSpec extends Specification {

    @Unroll
    def "Amount #amount is valid ? #isAmountValid"(float amount, boolean isAmountValid){
        when:
            boolean result = new AmountValidation({ -> "amount" } ).isValid(amount)
        then:
            result == isAmountValid
        where:
            amount || isAmountValid
            1.0    || true
            -1.0   || false
            0.0    || false
    }

    /*static class TradeValidatorSpec extends Specification {

        def "Testing All conditions"(){
            given:
            Instant now = Instant.now();
            Timestamp timestamp = Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build()
                Trade trade = Trade.newBuilder().setDate(timestamp).setAmount(1.0).setBroker("Hllo").setTrader("AsD").setPrice(1).build()
            when:
                boolean result = TradeValidation.allConditions.test(trade)
            then:
                result
        }

    }*/
}
