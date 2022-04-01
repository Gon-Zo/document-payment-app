package com.example.documentapproval.config.exception;

import com.example.documentapproval.enums.MessageType;

/** 데이터가 없을때 exception */
public class NoDataException extends BaseException {

  public NoDataException(MessageType msgType) {
    super(msgType);
  }
}
