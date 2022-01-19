package io.example.documentapproval.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table
public class Authority {

    @Id
    private String title;

}
