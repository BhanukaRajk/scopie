package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateDTO {
    private String userName;
    private String firstName;
    private String lastName;
}
