package com.sda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;
}
