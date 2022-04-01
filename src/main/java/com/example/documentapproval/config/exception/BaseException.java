package com.example.documentapproval.config.exception;

import com.example.documentapproval.enums.MessageType;

/**
 * 어플리케이션의 기본 exception
 */
public class BaseException extends RuntimeException {

  protected final MessageType msgType;

  protected String[] msgArgs;

  public BaseException(MessageType msgType) {
    this.msgType = msgType;
    this.msgArgs = new String[] {};
  }

  public BaseException(MessageType msgType, Throwable throwable) {
    super(throwable);
    this.msgType = msgType;
    this.msgArgs = new String[] {};
  }

  public BaseException(MessageType msgType, String[] msgArgs) {
    this.msgType = msgType;
    this.msgArgs = msgArgs;
  }

  public MessageType getMsgType() {
    return msgType;
  }

  public String[] getMsgArgs() {
    return msgArgs;
  }
}
