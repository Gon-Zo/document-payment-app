package com.example.documentapproval.config.security;

import com.example.documentapproval.config.security.token.AccessToken;
import com.example.documentapproval.domain.User;
import com.example.documentapproval.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationSuccessHandler")
@RequiredArgsConstructor
public class DomainSuccessHandler implements AuthenticationSuccessHandler {

  @Value("${jwt.key}")
  private String jwtKey;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    DomainUser domainUser = (DomainUser) authentication.getPrincipal();

    User user = domainUser.getUser();

    String accessToken = new AccessToken(user, jwtKey).createdToken();

    Message.Login loginMessage = Message.Login.builder().accessToken(accessToken).build();

    HttpUtils.createMessage(HttpStatus.OK, response, loginMessage);
  }
}
