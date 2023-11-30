package com.scopie.authservice.service;

import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Customer findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }

    public String authenticateUser(String username, String password) {
        // GET USER'S DATA FROM DATABASE USERNAME AND PASSWORD ONLY
        Customer customer = customerRepo.findByUsername(username);

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
        Customer newCustomer = new Customer(
                null,
                validationDTO.getFirstName(),
                validationDTO.getLastName(),
                validationDTO.getEmail(),
                passwordEncoder.encode(validationDTO.getPassword())
        );
        customerRepo.save(newCustomer);
    }

    public void changePassword(String username, String new_password) {
        try {
//            customerRepo.updatePasswordByEmail(username, passwordEncoder.encode(new_password));
            Customer customer = customerRepo.findByUsername(username);
            customer.setPassword(passwordEncoder.encode(new_password));
            customerRepo.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error changing password");
        }
    }

}
