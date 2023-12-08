package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String userName;
    private String oldPassword;
    private String newPassword;
    private String confPassword;
}
