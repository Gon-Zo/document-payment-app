package com.example.documentapproval.config.security.token;

import com.example.documentapproval.domain.User;

import java.time.Duration;
import java.util.Date;

/**
 * Access Token 객체
 */
public class AccessToken extends AbstractToken {

  public AccessToken(User user, String secretKey) {
    super(user, secretKey);
  }

  @Override
  public String createdToken() {
    Date expiration = new Date(new Date().getTime() + Duration.ofMinutes(30).toMillis());
    return createToken(expiration);
  }
}
