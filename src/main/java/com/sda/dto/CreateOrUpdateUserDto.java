package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateUserDto {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
