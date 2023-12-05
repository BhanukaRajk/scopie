package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class PasswordChangeDTO {
    private String userName;
    private String oldPassword;
    private String newPassword;
    private String confPassword;
}
