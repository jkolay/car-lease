package com.carlease.lease.service;

import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseResponse;

public interface CarLeaseService {
        LeaseResponse calculateCarLease(LeaseRequest leaseRequest);

        LeaseResponse createNewLease(LeaseRequest leaseRequest) throws Exception;

}
