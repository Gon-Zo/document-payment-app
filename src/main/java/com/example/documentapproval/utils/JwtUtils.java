package com.example.documentapproval.utils;

import com.example.documentapproval.config.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

  public static Claims parseJwtToken(String authorizationHeader, String jwtKey) {
    validationAuthorizationHeader(authorizationHeader);
    String passerHeader = extractToken(authorizationHeader);
    return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(passerHeader).getBody();
  }

  private static void validationAuthorizationHeader(String header) {
    if (null == header || "" == header || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      throw new IllegalArgumentException();
    }
  }

  private static String extractToken(String header) {
    return header.substring(SecurityConstants.TOKEN_PREFIX.length());
  }

  private static String createToken(
      String secretKey, Long id, String email, String roleName, Date expiration) {
    Date now = new Date();
    return Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .setIssuer("fresh")
        .setIssuedAt(now)
        .setExpiration(expiration)
        .claim("id", id)
        .claim("email", email)
        .claim("role", roleName)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }
}
