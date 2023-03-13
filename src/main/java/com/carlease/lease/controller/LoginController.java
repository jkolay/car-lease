package com.carlease.lease.controller;

import com.carlease.lease.model.request.UserRequestModel;
import com.carlease.lease.persistence.Customer;
import com.carlease.lease.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestModel userRequestModel) {
        return userManagementService.registerUser(userRequestModel);

    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        return userManagementService.getUserDetailsAfterLogin(authentication);
    }

}
