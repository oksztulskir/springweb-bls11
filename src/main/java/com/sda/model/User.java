package com.sda.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"login", "firstName", "lastName"})
public class User extends BaseEntity {
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String password;

    @ManyToMany
    @JoinTable(name = "user_address", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "address_id", referencedColumnName = "id")
    })
    private Set<Address> addresses;

    @Column(nullable = false)
    private boolean enabled;
}
