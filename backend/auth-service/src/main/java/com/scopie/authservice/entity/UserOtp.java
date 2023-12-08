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

    public Duration getDuration() {
        Duration duration = Duration.between(this.createdAt.toLocalDateTime(), Timestamp.valueOf(java.time.LocalDateTime.now()).toLocalDateTime());
        return duration;
    }

    public void setEmailAndOtp(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
