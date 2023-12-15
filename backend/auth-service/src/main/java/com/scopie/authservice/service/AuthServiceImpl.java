package com.scopie.authservice.service;

import com.scopie.authservice.dto.PasswordChangeDTO;
import com.scopie.authservice.dto.ProfileUpdateDTO;
import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public void signUp(ValidationDTO validationDTO) {

        customerRepo.save(Customer.builder()
                .firstName(validationDTO.getFirstName())
                .lastName(validationDTO.getLastName())
                .email(validationDTO.getEmail())
                .password(passwordEncoder.encode(validationDTO.getPassword()))
                .build()
        );
    }

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

    public boolean updatePassword(PasswordChangeDTO updatedPasswords) {
        Customer customer = customerRepo.findByEmail(updatedPasswords.getUserName());

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
    }

    public ProfileUpdateDTO getUserDetails(String username) {
        Customer customer = customerRepo.findByEmail(username);
        return new ProfileUpdateDTO(
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName()
        );
    }

}
