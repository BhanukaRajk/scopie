package com.scopie.authservice.service;

import com.scopie.authservice.dto.PasswordChangeDTO;
import com.scopie.authservice.dto.ProfileUpdateDTO;
import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.Customer;

import javax.naming.CannotProceedException;

public interface AuthService {

    String authenticateUser(String username, String password);

    Customer findByUsername(String username);

    void signUp(ValidationDTO signupDTO) throws CannotProceedException;

    void changePassword(String username, String password);

    void updateAccName(ProfileUpdateDTO updatedName);

    boolean updatePassword(PasswordChangeDTO updatedPasswords);

    ProfileUpdateDTO getUserDetails(String username);

}
