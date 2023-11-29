package com.scopie.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    // SEND OTP CODE TO THE PASSWORD FORGOTTEN USERS
    @PostMapping("/forgot-password/change-password")
    public String changePassword(String username) {
        return "Password changed";
    }

    //  SEND THE OTP CODE TO THE NEW USERS TO VERIFY THE EMAIL
    @PostMapping("/sign-up")
    public String signUp(String username) {
        return "Sign up successful";
    }
}
