package com.scopie.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return this.password;
    }
}
