package io.devmint.finance.trade.accounting.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public interface Validator<T>{

    default  List<Validator<T>> getValidations(){
        return Arrays.asList(this);
    }

    Predicate<T> getCondition();

    String getErrorMsg(T t);

    default boolean isValid(T t){
        return getCondition().test(t);
    }
    default boolean hasErrors(T t){
        return !isValid(t);
    }

    default List<String> getErrors(T parameter){

        Map<Validator<T>, String> errors = getValidations().stream().filter(validator -> validator.hasErrors(parameter))
                                                                    .collect(Collectors.toMap(validator -> validator, validator -> validator.getErrorMsg(parameter)));

        return errors.values().stream().collect(Collectors.toList());

    }

    default String genericErrorMsg(Supplier<String> fieldName,Supplier<String> fieldValue){
        return String.format("%s=%s is not valid", fieldName.get(),fieldValue.get());
    }


}
