package com.scopie.authservice.service;

import com.scopie.authservice.dto.SignupDTO;
import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.User;

public interface AuthService {


    public String authenticateUser(String username, String password) throws Exception;

    public User findByUsername(String username);

    public void signUp(ValidationDTO signupDTO);

    public void changePassword(String username, String password);

    public User findByUsernameAndPassword(String username, String password);
}
