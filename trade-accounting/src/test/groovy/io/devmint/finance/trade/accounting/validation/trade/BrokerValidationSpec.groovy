package io.devmint.finance.trade.accounting.validation.trade

import io.devmint.finance.trade.accounting.model.Trade

import spock.lang.Specification
import spock.lang.Unroll

class BrokerValidationSpec extends Specification {

    @Unroll
    def "Broker = \"#brokerName\" is valid ? #isValidBrokerName"(String brokerName, boolean isValidBrokerName) {
        given:
            Trade trade = Trade.newBuilder().setBroker(brokerName).build()
        when:
            boolean result = new BrokerValidation({->"broker"}).isValid(brokerName);
        then:
            result == isValidBrokerName
        where:
            brokerName                    || isValidBrokerName
            "The Broker"                  || true
            ""                            || false
            "The Broker with spaces   "   || true
    }
}
