package com.scopie.authservice.repository;

import com.scopie.authservice.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    @Query(value = "SELECT * FROM x_usr_otp WHERE email = ?1", nativeQuery = true)
    UserOtp findOtpByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM x_usr_otp WHERE email = ?1", nativeQuery = true)
    void deleteByEmail(String email);

}
