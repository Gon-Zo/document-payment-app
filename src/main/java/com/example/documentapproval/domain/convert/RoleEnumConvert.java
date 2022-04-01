package com.example.documentapproval.domain.convert;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.enums.RoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class RoleEnumConvert implements AttributeConverter<RoleEnum, String> {

  @Override
  public String convertToDatabaseColumn(RoleEnum attribute) {
    return Optional.ofNullable(attribute)
        .orElseThrow(() -> new NoDataException(MessageType.NoRoleData))
        .name();
  }

  @Override
  public RoleEnum convertToEntityAttribute(String dbData) {
    if (dbData.isEmpty()) {
      throw new NoDataException(MessageType.NoRoleData);
    }
    return RoleEnum.valueOf(dbData);
  }
}
