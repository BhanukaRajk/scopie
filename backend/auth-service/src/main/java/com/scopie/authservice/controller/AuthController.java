package com.scopie.authservice.controller;

import com.scopie.authservice.component.JwtGenerator;
import com.scopie.authservice.dto.*;

import com.scopie.authservice.service.AuthService;

import com.scopie.authservice.service.EmailService;
import com.scopie.authservice.service.OtpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtGenerator jwtGenerator;

    private ValidationController validator = new ValidationController();


    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody LoginDTO credentials) {
        if (validator.emailValidator(credentials.getUsername())) {
            try {
                String authenticationResult = authService.authenticateUser(credentials.getUsername(), credentials.getPassword());
                if (Objects.equals(authenticationResult, "false")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password!"));
                } else {
                    // GENERATE JWT TOKEN
                    String jwtToken = jwtGenerator.generateToken(credentials);

                    // CREATE AN HTTP ONLY COOKIE WITH THE JWT TOKEN
                    HttpCookie cookie = ResponseCookie.from("jwt-token", jwtToken)
                            .httpOnly(true)
                            .path("/")
                            .maxAge(3600) // SET THE COOKIE EXPIRATION TIME AS 1 HOUR
                            .build();

                    // SEND THE COOKIE ALONG WITH THE RESPONSE
                    return ResponseEntity.status(HttpStatus.OK)
                            .header("Set-Cookie", cookie.toString())
                            .body(Map.of("message", "Login successful!"));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", e.getMessage()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Please enter xxx@xxx.xx format!"));
        }
    }

    // LOGIN FUNCTIONALITY
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO credentials) {

        if (validator.emailValidator(credentials.getUsername())) {
            try {
                if (Objects.equals(authService.authenticateUser(credentials.getUsername(), credentials.getPassword()), "false")) {
                    return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(Map.of("error", "Invalid username or password!"));
                } else {
                    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", jwtGenerator.generateToken(credentials)));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(Map.of("error", "Please enter valid email format!"));
        }

    }

    // SIGNUP DATA VALIDATION + VERIFICATION EMAIL SENDING
    @PostMapping("/signup-validation")
    public ResponseEntity<String> signUp(@RequestBody SignupDTO signupReq) {

        // VALIDATE EMAIL
        if (!validator.emailValidator(signupReq.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a valid email address!");
        }

        // CHECK IF THERE IS AN EMAIL ALREADY REGISTERED
        if (authService.findByUsername(signupReq.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address already exists!");
        }

        // VALIDATE FIELDS ARE NOT EMPTY
        if (signupReq.getFirstName().isEmpty() || signupReq.getEmail().isEmpty() || signupReq.getPassword().isEmpty() || signupReq.getConfPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill all the fields!");
        }

        // VALIDATE PASSWORD MATCHING
        if (!Objects.equals(signupReq.getPassword(), signupReq.getConfPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not match!");
        }

        // VALIDATE PASSWORD LENGTH
        if (signupReq.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters!");
        }

        try {
            // EMAIL VALIDATION
            emailService.sendEmail(signupReq.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Verification code sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification code sending failed!");
        }
    }

    // SIGNUP USER CREATION
    @PostMapping("/signup-verification")
    public ResponseEntity<String> createAccount(@RequestBody ValidationDTO validationDTO) {

        // VALIDATE EMAIL
        if (!validator.emailValidator(validationDTO.getEmail())) {
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

        if (validationDTO.getOtp().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter the OTP!");
        }

        if (!otpService.compareOtp(validationDTO.getEmail(), validationDTO.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP!");
        }

        try {
            // EMAIL VALIDATION
            authService.signUp(validationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully!");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address already exists!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer adding failed!");
        }
    }


    // FORGOT PASSWORD EMAIL VALIDATION + OTP SEND REQUEST (#250)
    @PostMapping("/forgot-password/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody EmailDTO userEmail) {
        if (userEmail != null) {
            boolean userExist = (!Objects.equals(authService.findByUsername(userEmail.getEmail()), null));
            if (userExist) { // CHECK WHETHER THE USERNAME IS EXIST IN THE USERS TABLE
                try {
                    emailService.sendEmail(userEmail.getEmail());
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", "Verification code sent!"));
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Verification code sending failed!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(Map.of("error", "User not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(Map.of("error", "Username cannot be null!"));
        }
    }


    // ENTER EMAIL TO GET THE VERIFICATION CODE FOR CHANGE PASSWORD (#001)
    @PostMapping("/forgot-password/resend-one-time-passcode")
    public void resendCode(@NonNull @RequestBody EmailDTO userEmail) {
        boolean userExist = (!Objects.equals(authService.findByUsername(userEmail.getEmail()), null));
        if (userExist) {
            try {
                emailService.sendEmail(userEmail.getEmail());
            } catch (Exception e) {
                throw new RuntimeException("Email sending failed!");
            }
        }
    }


    // FORGOT PASSWORD USER VALIDATION + OTP REMOVAL
    @PostMapping("/forgot-password/verify-user")
    public Map<String, String> verifyUser(@RequestBody OtpDTO userOtp) {
        if (userOtp.getOtp() != null) {
            if (otpService.compareOtp(userOtp.getEmail(), userOtp.getOtp())) {
                return Map.of("success", "OTP verified successfully!");
            } else {
                return Map.of("error", "Invalid OTP!");
            }
        } else {
            return Map.of("error", "OTP cannot be empty!");
        }
    }

    // CHANGE PASSWORD FUNCTIONALITY + ADDING NEW PASSWORD TO THE DATABASE
    @PutMapping("/forgot-password/change-password") // TO CHANGE THE PASSWORD OF THE USER
    public Map<String, String> changePassword(@RequestBody ForgetPasswordDTO newPasswords) {
        if (Objects.equals(newPasswords.getPassword(), newPasswords.getConfPassword())) {
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

    // CHANGE ACCOUNT SETTINGS
    @PatchMapping("/account/update")
    public ResponseEntity<String> updateAccountName(@RequestBody ProfileUpdateDTO updatedName, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            if(updatedName.getUserName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account update failed!");
            } else if (updatedName.getFirstName() == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("First name cannot be null!");
            } else {
                try {
                    authService.updateAccName(updatedName);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account updated!");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating account!");
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // GET ACCOUNT DETAILS FOR UPDATE
    @GetMapping("/account/update/")
    public ResponseEntity<ProfileUpdateDTO> getProfileData(@RequestParam EmailDTO userName, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            try {
                ProfileUpdateDTO currentUser = authService.getUserDetails(userName.getEmail());
                return ResponseEntity.status(HttpStatus.OK).body(currentUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // ACCOUNT PASSWORD CHANGE FROM INSIDE
    @PatchMapping("/account/change-password")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody PasswordChangeDTO updatedPasswords, @RequestHeader(name = "Authorization", required = true) String authorizationHeader) {
        if((jwtGenerator.validateToken(authorizationHeader.split(" ")[1]))) {
            if (Objects.equals(updatedPasswords.getNewPassword(), updatedPasswords.getConfPassword())) {
                try {
                    if (authService.updatePassword(updatedPasswords)) {
                        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success","Password updated!"));
                    } else {
                        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("error", "Invalid current password!"));
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Password updating process failed!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("error", "Password does not match!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized request!"));
        }
    }









    // TODO: USED THE PARAMETERS TO SEND THE EMAIL WITH GET REQUEST (PLEASE REMOVE #001 OR #002)
    // ENTER EMAIL TO GET THE VERIFICATION CODE FOR CHANGE PASSWORD
    @GetMapping("/forgot-password/resend-one-time-passcode")
    public ResponseEntity<String> resendCode(@RequestParam(name = "email") String userEmail) {
        boolean userExist = (!Objects.equals(authService.findByUsername(userEmail), null));
        if (userExist) {
            try {
                emailService.sendEmail(userEmail);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verification code sent!");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification code sending failed!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
    }


    // TODO: THIS IS CREATED USING GET REQUEST PLEASE REMOVE #250 OR #251)
    // FORGOT PASSWORD EMAIL VALIDATION + OTP SEND REQUEST
    @GetMapping("/forgot-password/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "email") String userEmail) {
        if (userEmail != null) {
            boolean userExist = (!Objects.equals(authService.findByUsername(userEmail), null));
            if (userExist) { // CHECK WHETHER THE USERNAME IS EXIST IN THE USERS TABLE
                try {
                    emailService.sendEmail(userEmail);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Verification code sent!");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification code sending failed!");

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username cannot be null!");
        }
    }

}
