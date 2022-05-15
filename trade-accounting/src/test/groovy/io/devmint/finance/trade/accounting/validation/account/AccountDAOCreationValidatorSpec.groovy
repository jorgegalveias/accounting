package io.devmint.finance.trade.accounting.validation.account

import com.google.type.Money
import io.devmint.finance.trade.accounting.service.CreateAccountRequest
import spock.lang.Specification
import spock.lang.Unroll

class AccountDAOCreationValidatorSpec extends Specification {

    @Unroll
    def "Account Request with any accountId [#anyValue] is valid"(String anyValue){
        given:
            CreateAccountRequest request = CreateAccountRequest.newBuilder()
                                                               .setAccountId(anyValue)
                                                               .setBroker("valid broker")
                                                               .setCurrency("EUR")
                                                               .build()
        when:
            def isValid = new AccountCreationValidator().isValid(request)
        then:
            isValid == true
        where:
            anyValue << ["","anyValue","-1","anyId"]
    }

    @Unroll
    def "Account with currency = #currency is valid ? #isValidExpected"(String currency, boolean isValidExpected){
        given:
            CreateAccountRequest request = CreateAccountRequest.newBuilder().setAccountId("Account Id")
                .setBroker("valid broker")
                .setCurrency(currency)
                .build()
        when:
            def isValid = new AccountCreationValidator().isValid(request)

        then:
            isValid == isValidExpected
        where:
            currency  || isValidExpected
            ""        || false
            "-1"      || false
            "A"       || false
            "EURA"    || false
            "EUR"     || true
            "USD"     || true
    }

    @Unroll
    def "Account with broker = '#broker' is valid ? #isValidExpected"(String broker, boolean isValidExpected){
        given:
            CreateAccountRequest request = CreateAccountRequest.newBuilder()
                                                               .setAccountId("Account Id")
                                                               .setBroker(broker)
                                                               .setCurrency("EUR")
                                                               .build()
        when:
            def isValid = new AccountCreationValidator().isValid(request)

        then:
            isValid == isValidExpected
        where:
        broker              || isValidExpected
        ""                  || false
        "-1"                || true
        "A"                 || true
        "broker1"           || true
        "broker-1"          || true
        "anybrokername"     || true
    }

}
