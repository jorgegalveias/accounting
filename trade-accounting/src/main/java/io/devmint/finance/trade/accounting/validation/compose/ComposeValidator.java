package io.devmint.finance.trade.accounting.validation.compose;

import io.devmint.finance.trade.accounting.validation.Validator;
import io.devmint.finance.trade.accounting.validation.field.FieldValidator;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ComposeValidator<TYPE>  implements Validator<TYPE> {

    private final List<Validator<TYPE>> validations;
    private final Predicate<TYPE> condition;

    public ComposeValidator(List<Validator<TYPE>> validations){
        this.validations = validations;
        this.condition = validations.stream()
                .map(Validator::getCondition)
                .reduce(x -> true, Predicate::and);
    }
    @Override
    public Predicate<TYPE> getCondition() {
        return this.condition;
    }

    @Override
    public String getErrorMsg(TYPE TYPE) {
        return getErrors(TYPE).stream().collect(Collectors.joining(". "));
    }

    @Override
    public List<Validator<TYPE>> getValidations() {
        return this.validations;
    }

    public static <TYPE,FIELD> Validator<TYPE> fromFieldValidator(Function<TYPE,FIELD> fieldValue, Validator<FIELD> validator) {
        return new FieldValidator<TYPE>(createAccount -> validator.getCondition().test(fieldValue.apply(createAccount)), createAccount -> validator.getErrorMsg(fieldValue.apply(createAccount)));
    }
}
