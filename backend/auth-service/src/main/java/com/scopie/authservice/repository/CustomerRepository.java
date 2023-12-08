package com.scopie.authservice.repository;

import com.scopie.authservice.dto.ProfileUpdateDTO;
import com.scopie.authservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM t_customer WHERE email = ?1", nativeQuery = true) // ?1 IS THE FIRST PARAMETER
    Customer findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_customer SET first_name = ?2, last_name = ?3 WHERE email = ?1", nativeQuery = true)
    void updateNamesByEmail(String userName, String firstName, String lastName);
}
