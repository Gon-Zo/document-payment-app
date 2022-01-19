package io.example.documentapproval.domain;

import lombok.Getter;

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

}
