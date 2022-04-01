package com.example.documentapproval.config;

import com.example.documentapproval.config.security.JwtExceptionFilter;
import com.example.documentapproval.config.security.JwtLoginFilter;
import com.example.documentapproval.config.security.JwtValidFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${jwt.key}")
  private String jwtKey;

  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  private final AuthenticationFailureHandler authenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .disable()
        .formLogin()
        .disable()
        .addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtValidFilter(jwtKey), UsernamePasswordAuthenticationFilter.class);
  }

  private JwtLoginFilter jwtLoginFilter() throws Exception {
    return new JwtLoginFilter(
        "/login",
        authenticationManagerBean(),
        authenticationSuccessHandler,
        authenticationFailureHandler);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
