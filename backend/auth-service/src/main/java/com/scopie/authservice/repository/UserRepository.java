package com.scopie.authservice.repository;

import com.scopie.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
