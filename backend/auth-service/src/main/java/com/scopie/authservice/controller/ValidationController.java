package com.scopie.authservice.controller;

import java.util.regex.Pattern;

public class ValidationController {

    public boolean emailValidator(String email) {
        // VALID EMAIL PATTERN
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        // SEND STATUS
        return Pattern.compile(regexPattern).matcher(email).matches();
    }


}
