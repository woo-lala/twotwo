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
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{4,10}$", message = "username 은 영문과 숫자 포함 4자리이상 10자리이하로 입력해 주세요.")
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    @Email
    private String email;
    private boolean is_public;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=])[a-zA-Z0-9!@#$%^&*()_+-=]{8,15}$", message = "비밀번호는 영문과 숫자, 특수문자 포함 8자리이상 15자리이하로 입력해 주세요.")
    private String password;
}
