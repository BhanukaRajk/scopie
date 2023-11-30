package com.scopie.authservice.controller;

import com.scopie.authservice.config.JwtGeneratorImpl;
import com.scopie.authservice.dto.*;

import com.scopie.authservice.service.AuthService;

import com.scopie.authservice.service.EmailService;
import com.scopie.authservice.service.OtpService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // LOGIN FUNCTIONALITY
    @PostMapping ("/login")
    public Map<String, String> login(@RequestBody LoginDTO loginReq) throws Exception {

        String regex = "^(.+)@(.+)$"; // VALIDATE THE EMAIL BEFORE DATABASE QUERY

        if (loginReq.getUsername().matches(regex)) {
            try {
                if(Objects.equals(authService.authenticateUser(loginReq.getUsername(), loginReq.getPassword()), "false")) {
                    return Map.of("error", "Invalid username or password!");
                } else {
                    return new JwtGeneratorImpl().generate(loginReq);
                }
            } catch (Exception e) {
                return Map.of("error", e.getMessage());
            }
        } else {
            return Map.of("error", "Please enter xxx@xxx.xx format!");
        }
    }

    // SIGNUP DATA VALIDATION + SIGNUP FUNCTIONALITY
    @PostMapping("/signup-validation")
    public ResponseEntity<String> signUp(@RequestBody SignupDTO signupDTO) {
        System.out.println(signupDTO);

        // VALIDATE EMAIL PATTERN
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPattern).matcher(signupDTO.getEmail()).matches()) {
            System.out.println("Email validation failed!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a valid email address!");
        }

        if(authService.findByUsername(signupDTO.getEmail()) != null) {
            System.out.println("Email dup!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address already exists!");
        }

        // VALIDATE FIELDS ARE NOT EMPTY
        if (signupDTO.getFirstName().isEmpty() || signupDTO.getEmail().isEmpty() || signupDTO.getPassword().isEmpty() || signupDTO.getConfPassword().isEmpty()) {
            System.out.println("empty!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all the fields!");
        }

        // VALIDATE PASSWORD MATCHING
        if (!Objects.equals(signupDTO.getPassword(), signupDTO.getConfPassword())) {
            System.out.println("pw mis!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not match!");
        }

        // VALIDATE PASSWORD LENGTH
        if (signupDTO.getPassword().length() < 8) {
            System.out.println("Epw len!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters!");
        }

        try {
            // EMAIL VALIDATION
            emailService.sendEmail(signupDTO.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Verification code sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification code sending failed!");
        }
    }

    @PostMapping("/signup-verification")
    public ResponseEntity<String> createAccount(@RequestBody ValidationDTO validationDTO) {

        // VALIDATE EMAIL PATTERN
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (!Pattern.compile(regexPattern).matcher(validationDTO.getEmail()).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a valid email address!");
        }

        // VALIDATE FIELDS ARE NOT EMPTY
        if (validationDTO.getFirstName().isEmpty() || validationDTO.getEmail().isEmpty() || validationDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all the fields!");
        }

        // VALIDATE PASSWORD LENGTH
        if (validationDTO.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters!");
        }

        if(validationDTO.getOtp().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter the OTP!");
        }

        if(!otpService.compareOtp(validationDTO.getEmail(), validationDTO.getOtp())) {
            System.out.println("otp mis!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP!");
        }

        try {
            // EMAIL VALIDATION
            authService.signUp(validationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully!");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer adding failed!");
        }
    }

    // FORGOT PASSWORD EMAIL VALIDATION + OTP GENERATION
    @PostMapping("/forgot-password/verify-email") // TO VERIFY THE EMAIL FROM THE SERVER
    public Boolean verifyEmail(@RequestBody EmailDTO userEmail) {
        if(userEmail != null) {
            boolean userExist = (!Objects.equals(authService.findByUsername(userEmail.getEmail()), null));
            if(userExist) {
                try {
                    emailService.sendEmail(userEmail.getEmail());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Email sending failed!");
                }
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("Username cannot be empty!");
        }
    }

    // FORGOT PASSWORD USER VALIDATION + OTP REMOVAL
    @PostMapping("/forgot-password/verify-user")
    public Map<String, String> verifyUser(@RequestBody OtpDTO userOtp) {
        if(userOtp.getOtp() != null) {
            if(otpService.compareOtp(userOtp.getEmail(), userOtp.getOtp())) {
                return Map.of("success", "OTP verified successfully!");
            } else {
                return Map.of("error", "Invalid OTP!");
            }
        } else {
            return Map.of("error","OTP cannot be empty!");
        }
    }

    // CHANGE PASSWORD FUNCTIONALITY + ADDING NEW PASSWORD TO THE DATABASE
    @PutMapping("/forgot-password/change-password") // TO CHANGE THE PASSWORD OF THE USER
    public Map<String, String> changePassword(@RequestBody ForgetPasswordDTO newPasswords) {
        if(Objects.equals(newPasswords.getPassword(), newPasswords.getConfPassword())) {
            try {
                authService.changePassword(newPasswords.getEmail(), newPasswords.getPassword());
                return Map.of("success", "Password reset process successful!");
            } catch (EntityNotFoundException failure) {
                // IF THE DATA COULD NOT BE STORED IN THE DATABASE PASSWORD WILL NOT BE CHANGED
                return Map.of("error", "Password reset process unsuccessful!");
            }
        } else {
            return Map.of("error", "Passwords does not match!");
        }
    }

}
