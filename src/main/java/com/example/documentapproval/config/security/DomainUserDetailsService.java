package com.example.documentapproval.config.security;

import com.example.documentapproval.domain.User;
import com.example.documentapproval.repository.UserRepository;
import com.example.documentapproval.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user =
        userRepository
            .findByEmail(username.toLowerCase())
            .orElseThrow(() -> new UsernameNotFoundException("login user " + username));

    List<SimpleGrantedAuthority> authorities = UserUtils.convertOf(user);

    return new DomainUser(user, authorities);
  }
}
