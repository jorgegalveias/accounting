package io.devmint.finance.trade.accounting.validation.trade

import com.google.protobuf.Timestamp
import io.devmint.finance.trade.accounting.model.Trade
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant
import java.time.temporal.ChronoUnit

class DateValidationSpec extends Specification {

    @Unroll
    def "Date #tradeDate is valid ? #isDateValid"(Instant tradeDate, boolean isDateValid){
        given:
            Timestamp timestamp = Timestamp.newBuilder().setSeconds(tradeDate.getEpochSecond()).setNanos(tradeDate.getNano()).build()
        when:
            boolean result = new DateValidation({-> "date"}).isValid(timestamp)
        then:
            result == isDateValid
        where:
            tradeDate                                       || isDateValid
            Instant.now()                                   || true
            Instant.now().plus(1, ChronoUnit.SECONDS)       || false
            Instant.now().minus(1,ChronoUnit.DAYS)          || true
            Instant.now().minus(1,ChronoUnit.SECONDS)       || true
    }
}
