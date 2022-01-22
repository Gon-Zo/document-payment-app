package io.example.documentapproval.domain;

import io.example.documentapproval.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Table
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "authority_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authrity_title", referencedColumnName = "title")
    )
    private Set<Authority> authoritySet = new HashSet();

    @Transient
    public List<GrantedAuthority> getAuthorities() {
        return this.authoritySet.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User) || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        User user = (User) o;
        return user.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.id.intValue();
    }
}
