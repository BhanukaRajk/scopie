package com.scopie.authservice.repository;

import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Transactional
    @Modifying
    @Query("update Customer c set c.password = ?1 where c.email = ?2")
    void updatePasswordByEmail(@NonNull String password, @NonNull String email);
    @Transactional
    @Modifying
    @Query("update Customer c set c.password = ?1 where c.password = ?2")
    int updatePasswordByPassword(@NonNull String password, @NonNull String password1);
    // IF THIS IS EMPTY, CUZ WE ARE USING THE JPA REPOSITORY FUNCTIONALITIES

    @Query(value = "SELECT email, password FROM customer WHERE email = ?1", nativeQuery = true) // ?1 IS THE FIRST PARAMETER
    public User findByUsername(String email);

    @Query(value = "SELECT email, password FROM customer WHERE email = ?1 AND password = ?2", nativeQuery = true)
    public User findByUsernameAndPassword(String username, String password);

}
