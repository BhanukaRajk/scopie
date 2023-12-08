package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDTO {
    private String email;
    private String password;
    private String confPassword;
}
