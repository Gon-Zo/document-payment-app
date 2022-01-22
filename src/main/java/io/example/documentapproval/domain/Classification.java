package io.example.documentapproval.domain;

import lombok.Getter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Table
@Entity
public class Classification implements Serializable {

    @Transient
    private final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Classification) || Hibernate.getClass(o) != Hibernate.getClass(this)) return false;
        Classification that = (Classification) o;
        return that.getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
