package com.example.documentapproval.domain.convert;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.enums.StateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class StateTypeConvert implements AttributeConverter<StateType, String> {

  @Override
  public String convertToDatabaseColumn(StateType attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new NoDataException(MessageType.EmptyStateType))
        .name();
  }

  @Override
  public StateType convertToEntityAttribute(String dbData) {
    if (dbData.isEmpty()) {
      throw new NoDataException(MessageType.EmptyStateType);
    }
    return StateType.valueOf(dbData);
  }
}
