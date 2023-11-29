package com.scopie.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Random;

@Component
public class OtpServiceImpl implements OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String generateOtp(String key) {
        Random random = new Random();

        int leftLimit = 48; // CHARACTER '0'
        int rightLimit = 57; // CHARACTER '9'
        int targetStringLength = 6; // LENGTH OF OTP

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // DELETE THE OLD OTP IF EXISTS
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }

        redisTemplate.opsForValue().set(key, generatedString);
        redisTemplate.expire(key, Duration.ofMinutes(5));
        return generatedString;
    }

    // VALIDATE OTP AND DELETE IT FROM REDIS
    public boolean validateOtp(String key, String enteredOTP) {
        String cacheOtp = redisTemplate.opsForValue().get(key);
        if(enteredOTP.equals(cacheOtp)) {
            redisTemplate.delete(key);
            return true;
        } else {
            return false;
        }
    }

    // GET OTP FROM REDIS TO COMPARE
    public String getOtp(String key) { // KEY IS EMAIL
        return redisTemplate.opsForValue().get(key);
    }

}