package io.example.documentapproval.enums;

import io.example.documentapproval.service.excpetion.AppException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StateCode implements BaseEnum<String> {

    NO("F"),
    OK("T"),
    WAIT("W"),
    ;

    private final String value;

    StateCode(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    private static final Map<String, StateCode> descriptions =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(StateCode::getValue, Function.identity())));

    public static StateCode findOf(String findValue) {
        return Optional.ofNullable(descriptions.get(findValue)).orElseThrow(()-> new AppException(ErrorCode.EMPTY_STATE_CODE));
    }

}
