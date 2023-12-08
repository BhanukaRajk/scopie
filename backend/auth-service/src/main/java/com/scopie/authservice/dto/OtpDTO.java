package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpDTO {
    private String email;
    private String otp;
}
