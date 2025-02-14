package com.sparta.twotwo.members.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    @Email
    private String email;
    private boolean in_public;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+-=]{8,15}$")
    private String password;
}
