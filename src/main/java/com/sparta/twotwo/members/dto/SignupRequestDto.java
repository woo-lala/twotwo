package com.sparta.twotwo.members.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "")
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "")
    private String password;
}
