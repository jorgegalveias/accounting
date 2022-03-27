package io.devmint.finance.trade.accounting

import io.devmint.finance.trade.accounting.model.Security
import spock.lang.Specification

class AppSpec extends Specification  {
    def "Hello first test"(){
        when:
            Security security = new Security()
        then:
            a == 1
    }
}
