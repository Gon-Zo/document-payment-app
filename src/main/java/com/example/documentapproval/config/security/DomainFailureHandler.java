package com.example.documentapproval.config.security;

import com.example.documentapproval.enums.ErrorType;
import com.example.documentapproval.enums.ExceptionType;
import com.example.documentapproval.utils.HttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 실패 처리를 위한 핸들러
 */
@Component("authenticationFailureHandler")
public class DomainFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    String exceptionMessage = getExceptionMessage(exception);

    Message.Error message =
        Message.Error.builder()
            .code(ErrorType.AUTHENTICATION_ERROR.getCode())
            .message(exceptionMessage)
            .detailMessage(
                org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(exception))
            .build();

    HttpUtils.createMessage(HttpStatus.UNAUTHORIZED, response, message);
  }

  /**
   * 로그인시 예외 처리 구분을 위한 메소드
   *
   * @param exception - AuthenticationException
   * @return String
   */
  private String getExceptionMessage(AuthenticationException exception) {
    ExceptionType authenticationTypes = ExceptionType.findOf(exception.getClass().getSimpleName());
    return authenticationTypes.getValue();
  }
}
