package com.example.documentapproval.utils;

import com.example.documentapproval.config.exception.BaseException;
import com.example.documentapproval.config.security.Message;
import com.example.documentapproval.enums.ErrorType;
import com.example.documentapproval.enums.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Http 메시지 과련된 유티리티 */
public class HttpUtils {

  public static void createMessage(HttpStatus status, HttpServletResponse response, Object message)
      throws IOException {
    response.reset();

    response.setStatus(status.value());

    response.setContentType("application/json");

    response.getWriter().write(convertObjectToJson(message));
  }

  /**
   * 클래스를 ObjectMapper 에 담아서 String 리턴
   *
   * @param object - 클래스 값
   * @return String
   */
  private static String convertObjectToJson(Object object) {
    if (null == object) return "";
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  /**
   * 에러 메시지를 만들기 위한 메소드
   *
   * @param status - http method
   * @param response - HttpServletResponse
   * @param ex - Exception
   * @param errorType - ErrorType
   * @throws IOException
   */
  public static void createErrorMessage(
      HttpStatus status, HttpServletResponse response, Exception ex, ErrorType errorType)
      throws IOException {
    Message.Error message = setMessage(ex, errorType);
    createMessage(status, response, message);
  }

  /**
   * 에러 메시지를 만들어 주는 메소드
   *
   * @param ex - Exception
   * @param errorType - ErrorType
   * @return Message.Error
   */
  public static Message.Error setMessage(Exception ex, ErrorType errorType) {
    ex.printStackTrace();

    String detailMessage = ExceptionUtils.getStackTrace(ex);

    return Message.Error.builder()
        .code(errorType.getCode())
        .message(errorType.getMessage())
        .detailMessage(detailMessage)
        .build();
  }

  /**
   * 에러 메시지를 만들어 주는 메소드
   *
   * @param ex - BaseException
   * @param messageType MessageType
   * @return Message.Error
   */
  public static Message.Error setMessage(BaseException ex, MessageType messageType) {
    ex.printStackTrace();

    String detailMessage = ExceptionUtils.getStackTrace(ex);

    return Message.Error.builder()
        .code(messageType.getCode())
        .message(messageType.getMessage())
        .detailMessage(detailMessage)
        .build();
  }
}
