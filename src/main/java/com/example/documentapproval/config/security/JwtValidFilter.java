package com.example.documentapproval.config.security;

import com.example.documentapproval.domain.User;
import com.example.documentapproval.utils.JwtUtils;
import com.example.documentapproval.utils.UserUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/** jwt 체크 필터 */
public class JwtValidFilter extends OncePerRequestFilter {

  private final String jwtKey;

  public JwtValidFilter(String jwtKey) {
    this.jwtKey = jwtKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    Claims claims = JwtUtils.parseJwtToken(authorization, jwtKey);

    Long id = Long.valueOf((Integer) claims.get("id"));

    String email = (String) claims.get("email");

    String role = (String) claims.get("role");

    User loginUser = User.loginUserBuilder().id(id).email(email).role(role).build();

    List<SimpleGrantedAuthority> authorities = UserUtils.convertOf(role);

    DomainUser domainUser = new DomainUser(loginUser, authorities);

    UsernamePasswordAuthenticationToken authorityUser =
        new UsernamePasswordAuthenticationToken(
            domainUser, domainUser.getPassword(), domainUser.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authorityUser);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
    excludeUrlPatterns.add("/login/**");
    excludeUrlPatterns.add("/signup/**");
    excludeUrlPatterns.add("/swagger-ui/**");
    excludeUrlPatterns.add("/swagger-resources/**");
    excludeUrlPatterns.add("/v3/**");

    return excludeUrlPatterns.stream()
        .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
  }
}
