package com.scopie.authservice.service;

import com.scopie.authservice.entity.UserOtp;
import com.scopie.authservice.repository.UserOtpRepository;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.internal.CurrentTimestampGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class OtpServiceImpl implements OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserOtpRepository userOtpRepository;

    // STORE THE OTP ON REDIS WITH EXPIRATION TIME
    public String storeOtpOnRedis(String key) {

        String generatedString = generateOtp();

        // DELETE THE OLD OTP IF EXISTS
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            stringRedisTemplate.delete(key);
        }

        stringRedisTemplate.opsForValue().set(key, generatedString);
        stringRedisTemplate.expire(key, Duration.ofMinutes(5));
        return generatedString;
    }

    // VALIDATE OTP AND DELETE IT FROM REDIS
    public boolean validateOtp(String key, String enteredOTP) {
        String cacheOtp = stringRedisTemplate.opsForValue().get(key);
        if(enteredOTP.equals(cacheOtp)) {
            stringRedisTemplate.delete(key);
            return true;
        } else {
            return false;
        }
    }

    // STORE OTP ON MYSQL DATABASE
    public String storeOtp(String email) {

        String otp = generateOtp();
        UserOtp userOtp = new UserOtp();

        userOtp.setEmailAndOtp(email, otp);

        // DELETE THE OLD OTP IF EXISTS
        userOtpRepository.deleteByEmail(email);
        userOtpRepository.save(userOtp);
        return otp;
    }

    // TODO: REMOVE THE OTP CODE FROM THE DATABASE IF REQUIRED
    // COMPARE GENERATED OTP AND USER INPUT OTP
    public boolean compareOtp(String email, String enteredOTP) {
        UserOtp cacheOtp = userOtpRepository.findOtpByEmail(email);
        return enteredOTP.equals(cacheOtp.getOtp()) && cacheOtp.isValid();
    }

    // STRING OTP GENERATOR
    public String generateOtp() {
        Random random = new Random();

        int leftLimit = 48; // CHARACTER '0'
        int rightLimit = 57; // CHARACTER '9'
        int targetStringLength = 6; // LENGTH OF OTP

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}