package io.devmint.finance.trade.accounting.validation.trade;

import com.google.protobuf.Timestamp;
import io.devmint.finance.trade.accounting.validation.Validator;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DateValidation implements Validator<Timestamp> {

    Supplier<String> fieldName;

    public DateValidation(Supplier<String> fieldName){
        Objects.requireNonNull(fieldName);
        this.fieldName = fieldName;
    }

    @Override
    public Predicate<Timestamp> getCondition() {
        return timestamp -> Instant.now().isAfter(Instant.ofEpochSecond(timestamp.getSeconds(),timestamp.getNanos()));
    }

    @Override
    public String getErrorMsg(Timestamp timestamp) {
        return genericErrorMsg(fieldName,() ->timestamp.toString());
    }
}
