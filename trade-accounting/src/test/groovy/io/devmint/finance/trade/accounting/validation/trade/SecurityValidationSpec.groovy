package io.devmint.finance.trade.accounting.validation.trade

import io.devmint.finance.trade.accounting.model.Security
import io.devmint.finance.trade.accounting.model.Trade
import io.devmint.finance.trade.accounting.validation.security.SecurityValidation
import spock.lang.Specification

class SecurityValidationSpec extends Specification {

    def "Security with empty fields is not valid"(){
        given:
            Security security = Security.newBuilder().build();
        when:
            boolean isSecurityValid = new SecurityValidation({->"security"}).isValid(security)
        then:
            isSecurityValid == false
    }
    def "Security with empty ISIN is not valid"(){
        given:
            Security security = Security.newBuilder().setName("anything").build();
        when:
            boolean isSecurityValid = new SecurityValidation({->"security"}).isValid(security)
        then:
            isSecurityValid == false
    }
    def "Security with empty name is not valid"(){
        given:
            Security security = Security.newBuilder().setIsin("anything").build();
        when:
            boolean isSecurityValid = new SecurityValidation({->"security"}).isValid(security)
        then:
            isSecurityValid == false
    }


}
