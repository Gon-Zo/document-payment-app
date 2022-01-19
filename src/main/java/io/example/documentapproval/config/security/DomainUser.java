package io.example.documentapproval.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class DomainUser extends User implements Serializable {

    private final Long serialVersionUID = 1L;

    private Long id;

    public DomainUser(io.example.documentapproval.domain.User user) {
        super(user.getEmail(), user.getPassword(), user.getAuthorities());
        this.id = user.getId();
    }

    public DomainUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public DomainUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Long getId() {
        return id;
    }

}
