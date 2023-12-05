package com.scopie.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    // GET THIS FROM THE .ENV FILE TO STORE THE EMAIL HOST
    @Value("${spring.mail.host}")
    private String hostEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OtpService otpService;

    // MAIL SENDING METHOD
    public void sendEmail(String recipientEmail) {
        try {
            // GENERATE THE OTP
//            String otp = otpService.generateOtp(recipientEmail);
            String otp = otpService.storeOtp(recipientEmail);

            // CREATE THE NEW MESSAGE
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // SET UP MESSAGE DETAILS
            mailMessage.setFrom(hostEmail);
            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject("Scopie Verification Code");
            mailMessage.setText("Your scopie verification code is: " + otp + ". This will valid only for 5 minutes!");

            // SEND THE MESSAGE
            javaMailSender.send(mailMessage);

        } catch (Exception error) {
            // HANDLE THE EXCEPTION
            throw new MailSendException(String.valueOf(error));
        }
    }
}
