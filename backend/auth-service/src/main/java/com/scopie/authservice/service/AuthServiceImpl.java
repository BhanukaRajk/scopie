package com.scopie.authservice.service;

import com.scopie.authservice.config.JwtGenerator;
import com.scopie.authservice.config.JwtGeneratorImpl;
import com.scopie.authservice.dto.LoginDTO;
import com.scopie.authservice.dto.ValidationDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.entity.User;
import com.scopie.authservice.repository.CustomerRepository;
import com.scopie.authservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialNotFoundException;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return customerRepo.findByUsername(username);
    }

//    public boolean checkIfUserExists(String username) {
//        return customerRepo.existsByUsername(username);
//    }

//    public String authenticateUser(String username, String password) throws Exception, UserNotFoundException, InvalidPasswordException {

    public String authenticateUser(String username, String password) {
        // GET USER'S DATA FROM DATABASE USERNAME AND PASSWORD ONLY
        User customer = customerRepo.findByUsername(username);

        // CHECK IF USER IS NULL
        if (customer != null) {

            // VALIDATE PASSWORD
            if (!passwordEncoder.matches(password, customer.getHashedPassword())) {
                return "false";
            }

            // GENERATE JWT TOKEN
            JwtGenerator jwtGenerator = new JwtGeneratorImpl();

            // RETURN JWT TOKEN
            return jwtGenerator.generate(modelMapper.map(customer, LoginDTO.class)).toString();

        } else  {
//            throw new NotFoundException("User not found");
            return "false";
        }
    }

    // ADDING NEW CUSTOMER
    public void signUp(ValidationDTO signupDTO) {
        customerRepo.save(modelMapper.map(signupDTO, Customer.class));
    }

    public void changePassword(String username, String password) {
        try {
            customerRepo.updatePasswordByEmail(username, passwordEncoder.encode(password));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error changing password");
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return customerRepo.findByUsernameAndPassword(username, password);
    }

}
