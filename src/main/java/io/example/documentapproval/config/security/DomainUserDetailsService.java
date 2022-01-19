package io.example.documentapproval.config.security;

import io.example.documentapproval.domain.User;
import io.example.documentapproval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loginUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("login user " + username));
        return new DomainUser(loginUser);
    }

}
