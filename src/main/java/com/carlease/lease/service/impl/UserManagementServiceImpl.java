package com.carlease.lease.service.impl;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.mapper.UserMapper;
import com.carlease.lease.model.request.UserRequestModel;
import com.carlease.lease.persistence.Authority;
import com.carlease.lease.persistence.Customer;
import com.carlease.lease.repository.AuthorityRepository;
import com.carlease.lease.repository.CustomerRepository;
import com.carlease.lease.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseEntity<String> registerUser(UserRequestModel userRequestModel) {
        Customer customer=userMapper.mapUserRequestModelToCustomer(userRequestModel);
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            if(!userRequestModel.getRole().equalsIgnoreCase("BROKER") && userRequestModel.getRole().equalsIgnoreCase("COMPANY")){
                throw new CarLeaseException("User role needs to be either Broker or Company");
            }
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            savedCustomer = customerRepository.save(customer);

            String authorityName= "ROLE_"+customer.getRole().toUpperCase();
            Authority authority=new Authority();
            authority.setName(authorityName);
            authority.setCustomer(savedCustomer);
            authorityRepository.save(authority);

            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @Override
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }
    }
}
