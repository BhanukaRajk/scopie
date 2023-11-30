package com.scopie.authservice.service;

import com.scopie.authservice.dto.OtpDTO;
import com.scopie.authservice.entity.UserOtp;
import com.scopie.authservice.repository.UserOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Random;

@Component
public class OtpServiceImpl implements OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserOtpRepository userOtpRepository;
    @Autowired
    private ModelMapper modelMapper;


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

    public String createOtp(String email) {
        Random random = new Random();

        int leftLimit = 48; // CHARACTER '0'
        int rightLimit = 57; // CHARACTER '9'
        int targetStringLength = 6; // LENGTH OF OTP

        String otp = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // DELETE THE OLD OTP IF EXISTS
        UserOtp userOtp = new UserOtp(null, email, otp);
        userOtpRepository.deleteByEmail(email);
        userOtpRepository.save(userOtp);
        return otp;
    }

    public boolean compareOtp(String email, String enteredOTP) {
        String cacheOtp = userOtpRepository.findOtpByEmail(email);
        System.out.println("cacheOtp: " + cacheOtp);
        if(enteredOTP.equals(cacheOtp)) {
            return true;
        } else {
            return false;
        }
    }


    // GET OTP FROM REDIS TO COMPARE
    public String getOtp(String key) { // KEY IS EMAIL
        return stringRedisTemplate.opsForValue().get(key);
    }

}