package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update customer set password = ?1 where email = ?2", nativeQuery = true)
    void updatePasswordByEmail(@NonNull String password, @NonNull String email);
    // IF THIS IS EMPTY, CUZ WE ARE USING THE JPA REPOSITORY FUNCTIONALITIES

    @Query(value = "SELECT * FROM customer WHERE email = ?1", nativeQuery = true) // ?1 IS THE FIRST PARAMETER
    public Customer findByUsername(String email);

    @Query(value = "SELECT * FROM customer WHERE email = ?1 AND password = ?2", nativeQuery = true)
    public Customer findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM user_otp WHERE email = ?1", nativeQuery = true)
    public UserOtp findByEmail(String email);

}
