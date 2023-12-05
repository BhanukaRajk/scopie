package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class ProfileUpdateDTO {
    private String userName;
    private String firstName;
    private String lastName;
}
