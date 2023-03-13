package com.carlease.lease.service;

import com.carlease.lease.model.request.UserRequestModel;
import com.carlease.lease.persistence.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserManagementService {
    ResponseEntity<String> registerUser(UserRequestModel userRequestModel);

    Customer getUserDetailsAfterLogin(Authentication authentication);
}
