package com.sda.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 */
@Entity
@Getter
@Setter
public class Role extends BaseEntity {
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles name;

    public enum Roles {
        USER, ADMIN
    }
}
