package com.scopie.authservice.service;

import com.scopie.authservice.dto.PasswordChangeDTO;
import com.scopie.authservice.dto.ProfileUpdateDTO;
import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.CannotProceedException;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Customer findByUsername(String username) {
        return customerRepo.findByEmail(username);
    }

    public String authenticateUser(String username, String password) {
        // GET USER'S DATA FROM DATABASE USERNAME AND PASSWORD ONLY
        Customer customer = customerRepo.findByEmail(username);

        // CHECK IF USER IS NULL
        if (customer != null) {
            // VALIDATE PASSWORD
            if (passwordEncoder.matches(password, customer.getPassword())) {
                return "true";
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }

    // ADDING NEW CUSTOMER
    public void signUp(ValidationDTO validationDTO) throws CannotProceedException {
        try {
            customerRepo.save(Customer.builder()
                    .firstName(validationDTO.getFirstName())
                    .lastName(validationDTO.getLastName())
                    .email(validationDTO.getEmail())
                    .password(passwordEncoder.encode(validationDTO.getPassword()))
                    .build()
            );
        } catch (Exception e) {
            throw new CannotProceedException("Could not create user account");
        }
    }

    // FORGOT PASSWORD PASSWORD CHANGER
    public void changePassword(String username, String new_password) {
        try {
            Customer customer = customerRepo.findByEmail(username);
            customer.setPassword(passwordEncoder.encode(new_password));
            customerRepo.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error changing password");
        }
    }

    // UPDATE USER'S NAMES
    public void updateAccName(ProfileUpdateDTO updatedName) {
        try {
            customerRepo.updateNamesByEmail(
                    updatedName.getUserName(),
                    updatedName.getFirstName(),
                    updatedName.getLastName()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating account!");
        }
    }

    // UPDATE USER PASSWORD
    public boolean updatePassword(PasswordChangeDTO updatedPasswords) {
        Customer customer = customerRepo.findByEmail(updatedPasswords.getUserName());

        try {
            // CHECK IF USER IS NULL
            if (customer != null) {
                // VALIDATE PASSWORD
                if (passwordEncoder.matches(updatedPasswords.getOldPassword(), customer.getPassword())) {
                    customer.setPassword(passwordEncoder.encode(updatedPasswords.getNewPassword()));
                    customerRepo.save(customer);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new NotFoundException("User not found!");
        }
    }

    // GET CURRENT USER'S DETAILS
    public ProfileUpdateDTO getUserDetails(String username) {
        try {
            Customer customer = customerRepo.findByEmail(username);
            return new ProfileUpdateDTO(
                    customer.getEmail(),
                    customer.getFirstName(),
                    customer.getLastName()
            );
        } catch (Exception e) {
            throw new NotFoundException("User not found!");
        }
    }

}
