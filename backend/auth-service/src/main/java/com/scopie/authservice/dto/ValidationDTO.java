package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String otp;

}
