package com.example.documentapproval.config.security;

import com.example.documentapproval.enums.ErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.documentapproval.utils.HttpUtils.createErrorMessage;

/** JWT filter 에서 발생되는 에러를 케치 하기 위한 필터 */
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {

      filterChain.doFilter(request, response);

    } catch (UnsupportedJwtException e) {
      // 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
      createErrorMessage(HttpStatus.FORBIDDEN, response, e, ErrorType.JWT_UNSUPPORTED);
    } catch (MalformedJwtException e) {
      // JWT가 올바르게 구서오디지 않았을 때
      createErrorMessage(HttpStatus.FORBIDDEN, response, e, ErrorType.JWT_MALFORMED);
    } catch (ExpiredJwtException e) {
      // JWT를 생성할 때 지정한 유효기간이 초과되었을 때
      createErrorMessage(HttpStatus.GONE, response, e, ErrorType.JWT_EXPIRED);
    } catch (SignatureException e) {
      // JWT의 기존 서명을 확인하지 못했을 때
      createErrorMessage(HttpStatus.GONE, response, e, ErrorType.JWT_SIGNATURE);
    } catch (Exception e) {
      createErrorMessage(
          HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorType.INTERNAL_SERVER_ERROR);
    }
  }
}
