package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    @NotBlank
    @Email
    protected String username;

    @NotBlank
    protected String password;
    @NotBlank
    protected String firstName;
    @NotBlank
    protected String lastName;
    protected String roles;

}
