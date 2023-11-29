package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confPassword;
    private String otp;

}
