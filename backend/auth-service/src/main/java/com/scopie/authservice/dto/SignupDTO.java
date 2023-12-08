package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confPassword;
}
