package com.carlease.lease.controller;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.service.CarLeaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class CarLeaseControllerTest {
    @Mock
    private CarLeaseService carLeaseServices;

    @InjectMocks
    CarLeaseController carLeaseController;

    @Test
    public void testCreateLease() throws CarLeaseException {
        Mockito.when(carLeaseServices.createNewLease(Mockito.any(LeaseRequest.class))).thenReturn(new LeaseResponse());
       Assertions.assertNotNull(carLeaseController.createLease(new LeaseRequest()));
    }

    @Test
    public void getMonthlyLeaseTest() throws Exception {
        Mockito.when(carLeaseServices.getLeaseAmountByCustomerId(Mockito.anyInt())).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(carLeaseController.getMonthlyLease(1));
    }

    @Test
    public void getLeaseDetailsByCarIdTest() throws CarNotFoundException {
        Mockito.when(carLeaseServices.getLeaseAmountByCarId(Mockito.anyInt())).thenReturn(new LeaseResponse());
        Assertions.assertNotNull(carLeaseController.getLeaseDetailsByCarId(1));
    }
}
