package com.example.documentapproval.config;

import com.example.documentapproval.config.exception.NoDataException;
import com.example.documentapproval.config.security.Message;
import com.example.documentapproval.enums.ErrorType;
import com.example.documentapproval.enums.MessageType;
import com.example.documentapproval.utils.HttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Exception 헨들러
   *
   * @param ex - Exception
   * @param response HttpServletResponse
   * @return ResponseEntity<Message.Error>
   */
  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Message.Error> handler(Exception ex, HttpServletResponse response) {
    ErrorType errorType = ErrorType.INTERNAL_SERVER_ERROR;
    response.reset();
    Message.Error message = HttpUtils.setMessage(ex, errorType);
    return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * NoDataException 핸들러
   *
   * @param ex - NoDataException
   * @param response - HttpServletResponse
   * @return ResponseEntity<Message.Error>
   */
  @ResponseBody
  @ExceptionHandler(NoDataException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Message.Error> handler(NoDataException ex, HttpServletResponse response) {
    MessageType errorType = ex.getMsgType();
    response.reset();
    Message.Error message = HttpUtils.setMessage(ex, errorType);
    return new ResponseEntity<Message.Error>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
