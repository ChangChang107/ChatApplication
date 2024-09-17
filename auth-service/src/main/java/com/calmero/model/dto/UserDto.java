package com.calmero.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String role;
    private String status;
    private String password;
}
