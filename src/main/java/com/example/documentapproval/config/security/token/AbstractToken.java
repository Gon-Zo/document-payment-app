package com.example.documentapproval.config.security.token;

import com.example.documentapproval.domain.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public abstract class AbstractToken {

  private final User user;

  private final String secretKey;

  protected AbstractToken(User user, String secretKey) {
    this.user = user;
    this.secretKey = secretKey;
  }

  public abstract String createdToken();

  protected String createToken(Date expiration) {
    return Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .setIssuer("fresh")
        .setIssuedAt(new Date())
        .setExpiration(expiration)
        .claim("id", user.getId())
        .claim("email", user.getEmail())
        .claim("role", user.getEmail())
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
