package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserSignIn {
    @NotBlank
    @Email
    protected String username;
    @NotBlank
    protected String password;
}
