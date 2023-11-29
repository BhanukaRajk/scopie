package com.scopie.authservice.service;

import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    public String generateOtp(String key);
    public String getOtp(String key);
    public boolean validateOtp(String key, String enteredOTP);
}
