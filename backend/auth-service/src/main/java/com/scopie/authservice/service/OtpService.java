package com.scopie.authservice.service;

import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    String storeOtpOnRedis(String key);
    boolean validateOtp(String key, String enteredOTP);
    boolean compareOtp(String email, String enteredOTP);
    String storeOtp(String email);
    String generateOtp();
}
