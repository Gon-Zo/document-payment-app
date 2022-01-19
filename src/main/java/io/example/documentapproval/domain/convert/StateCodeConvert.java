package io.example.documentapproval.domain.convert;

import io.example.documentapproval.enums.ErrorCode;
import io.example.documentapproval.enums.StateCode;
import io.example.documentapproval.service.excpetion.AppException;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class StateCodeConvert implements AttributeConverter<StateCode, String> {

    @Override
    public String convertToDatabaseColumn(StateCode attribute) {
        return Optional.ofNullable(attribute).orElseThrow(()-> new AppException(ErrorCode.EMPTY_STATE_CODE)).getValue();
    }

    @Override
    public StateCode convertToEntityAttribute(String dbData) {
        return StateCode.findOf(dbData);
    }

}
