package com.scopie.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Duration;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "x_usr_otp")
public class UserOtp {

    public enum OtpStatus {
        VALID,
        INVALID
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id", nullable = false)
    private Long otpId;

    @Column(name = "email", nullable = false)
    private String email;

    @Getter
    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "expiration_time", nullable = false)
    private Timestamp expirationTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OtpStatus status;


    @PrePersist
    private void setExpirationTime() {
        this.expirationTime = new Timestamp(createdAt.getTime() + (5 * 60 * 1000));
    }   // SET THE EXPIRATION TIME AFTER 5 MINUTES FROM THE CREATION TIME

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime.getTime();
    }   // CHECK WHETHER THE CODE IS EXPIRED OR NOT, IF THE STATUS IS STILL VALID

    public void updateStatus() {
        if (isExpired()) {
            this.status = OtpStatus.INVALID;
        } else {
            this.status = OtpStatus.VALID;
        }
    }   // UPDATE THE STATUS OF THE CODE

    public boolean isValid() {
        if(this.status == OtpStatus.VALID) {
            this.updateStatus();
            return this.status == OtpStatus.VALID;
        } else {
            return false;
        }
    }   // IF THE CODE STATUS IS STILL VALID, THEN UPDATE THE STATUS AND RETURN NEW STATUS

    public Duration getDuration() {
        return Duration.between(this.createdAt.toLocalDateTime(), Timestamp.valueOf(java.time.LocalDateTime.now()).toLocalDateTime());
    } // TODO: THIS IS THE OLD FUNCTION USED TO CHECK EXPIRATION OF THE OTP CODE

    public void setEmailAndOtp(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
