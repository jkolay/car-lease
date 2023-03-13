package com.carlease.lease.service.impl;

import com.carlease.lease.client.CarClientProperties;
import com.carlease.lease.client.CustomerClientProperties;
import com.carlease.lease.config.CarLeaseConstant;
import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.mapper.CarLeaseMapper;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.CarResponse;
import com.carlease.lease.model.response.CustomerResponse;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import com.carlease.lease.repository.CareLeaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Test Class to write service impl test cases
 */
@ExtendWith(MockitoExtension.class)
public class CarLeaseServiceImplTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    CarClientProperties carsCarClientProperties;

    @Mock
    CustomerClientProperties customerClientProperties;

    @Mock
    CarLeaseMapper carLeaseMapper;

    @Mock
    CareLeaseRepository careLeaseRepository;

    @InjectMocks
    CarLeaseServiceImpl carLeaseService;

    @Test
    public void createNewLeaseTest() throws CarLeaseException {
        ResponseEntity<CarResponse> carResponse = Mockito.mock(ResponseEntity.class);
        ArgumentCaptor<Class> argumentCaptor=ArgumentCaptor.forClass(Class.class);
        ResponseEntity<CustomerResponse> customer = Mockito.mock(ResponseEntity.class);
        CarResponse carResponse1=Mockito.mock(CarResponse.class);
        CustomerResponse customerResponse=Mockito.mock(CustomerResponse.class);
        CarLeaseDao carLeaseDao=Mockito.mock(CarLeaseDao.class);
        ArgumentCaptor<HttpEntity> argumentCaptor1= ArgumentCaptor.forClass(HttpEntity.class);
        LeaseResponse response=Mockito.mock(LeaseResponse.class);

        Mockito.when(carsCarClientProperties.getHostname()).thenReturn("localhost:8080");
        Mockito.when(carsCarClientProperties.getBaseUrl()).thenReturn("car");
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(),argumentCaptor.capture())).thenReturn(carResponse);
        Mockito.when(customerClientProperties.getHostname()).thenReturn("localhost");
        Mockito.when(customerClientProperties.getBaseUrl()).thenReturn("customer");
        Mockito.when(restTemplate.getForEntity(Mockito.eq("http://localhost/customer1"),argumentCaptor.capture())).thenReturn(customer);
        Mockito.when(carResponse.getBody()).thenReturn(carResponse1);
        Mockito.when(customer.getBody()).thenReturn(customerResponse);
        Mockito.when(carLeaseMapper.mapLeaseRequestToCarLeaseDao(Mockito.any(LeaseRequest.class))).thenReturn(carLeaseDao);
        Mockito.when(carResponse1.getMileage()).thenReturn(11223L);
        Mockito.when( careLeaseRepository.save(Mockito.any(CarLeaseDao.class))).thenReturn(carLeaseDao);
        Mockito.when(carResponse1.getCarId()).thenReturn(1);
        Mockito.when(carResponse1.getNetPrice()).thenReturn(11223.0);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.eq(HttpMethod.PUT),argumentCaptor1.capture(),argumentCaptor.capture())).thenReturn(carResponse);
        Mockito.when(restTemplate.exchange(Mockito.eq("http://localhost/customermodify/status/0"),Mockito.eq(HttpMethod.PUT),argumentCaptor1.capture(),argumentCaptor.capture())).thenReturn(customer);
        Mockito.when(carLeaseMapper.mapCarLeaseDaoToLeaseResponse(Mockito.any(CarLeaseDao.class), Mockito.any(CarResponse.class), Mockito.any(CustomerResponse.class))).thenReturn(response);
        LeaseRequest leaseRequest= new LeaseRequest(1,2,50, LocalDate.now());

        Assertions.assertNotNull(carLeaseService.createNewLease(leaseRequest));

    }

    @Test
    public void createNewLeaseTestWithException(){
        ResponseEntity<CarResponse> carResponse = Mockito.mock(ResponseEntity.class);
        ArgumentCaptor<Class> argumentCaptor=ArgumentCaptor.forClass(Class.class);
        Mockito.when(carsCarClientProperties.getHostname()).thenReturn("localhost:8080");
        Mockito.when(carsCarClientProperties.getBaseUrl()).thenReturn("car");
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(),argumentCaptor.capture())).thenReturn(carResponse);
        LeaseRequest leaseRequest= new LeaseRequest(1,2,50, LocalDate.now());
        Assertions.assertThrows(CarLeaseException.class,()->carLeaseService.createNewLease(leaseRequest));

    }

    @Test
    public void getLeaseAmountByCustomerIdTest() throws CustomerNotFoundException {
        ArgumentCaptor<Class> argumentCaptor=ArgumentCaptor.forClass(Class.class);
        ResponseEntity<CustomerResponse> customer = Mockito.mock(ResponseEntity.class);
        CustomerResponse customerResponse=Mockito.mock(CustomerResponse.class);
        CarLeaseDao carLeaseDao=Mockito.mock(CarLeaseDao.class);
        List<CarLeaseDao> carLeaseDaos=new ArrayList<>();
        carLeaseDaos.add(carLeaseDao);
        LeaseCalculationResponse leaseCalculationResponse=Mockito.mock(LeaseCalculationResponse.class);

        Mockito.when(customerClientProperties.getHostname()).thenReturn("localhost");
        Mockito.when(customerClientProperties.getBaseUrl()).thenReturn("customer");
        Mockito.when(restTemplate.getForEntity(Mockito.eq("http://localhost/customer1"),argumentCaptor.capture())).thenReturn(customer);
        Mockito.when(customer.getBody()).thenReturn(customerResponse);
        Mockito.when( careLeaseRepository.findByCustomerId(Mockito.anyInt())).thenReturn(carLeaseDaos);
        Mockito.when(carLeaseMapper.mapCarLeaseDaoToLeaseCalcResponse(carLeaseDao)).thenReturn(leaseCalculationResponse);

        Assertions.assertNotNull(carLeaseService.getLeaseAmountByCustomerId(1));

    }

    @Test
    public void getLeaseAmountByCarIdTest() throws CarNotFoundException {
        ResponseEntity<CarResponse> carResponse = Mockito.mock(ResponseEntity.class);
        ArgumentCaptor<Class> argumentCaptor=ArgumentCaptor.forClass(Class.class);
        CarResponse carResponse1=Mockito.mock(CarResponse.class);
        ResponseEntity<CustomerResponse> customer =Mockito.mock(ResponseEntity.class);
        CustomerResponse customerResponse=Mockito.mock(CustomerResponse.class);
        LeaseResponse leaseResponse=Mockito.mock(LeaseResponse.class);


        CarLeaseDao carLeaseDao=Mockito.mock(CarLeaseDao.class);
        Mockito.when(carsCarClientProperties.getHostname()).thenReturn("localhost:8080");
        Mockito.when(carsCarClientProperties.getBaseUrl()).thenReturn("car");
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(),argumentCaptor.capture())).thenReturn(carResponse);
        Mockito.when(carResponse.getBody()).thenReturn(carResponse1);
        Mockito.when(careLeaseRepository.findByCarId(Mockito.anyInt())).thenReturn(carLeaseDao);
        Mockito.when(carLeaseDao.getCustomerId()).thenReturn(1);
        Mockito.when(customerClientProperties.getHostname()).thenReturn("localhost");
        Mockito.when(customerClientProperties.getBaseUrl()).thenReturn("customer");
        Mockito.when(restTemplate.getForEntity(Mockito.eq("http://localhost/customer1"),argumentCaptor.capture())).thenReturn(customer);
        Mockito.when(customer.getBody()).thenReturn(customerResponse);
        Mockito.when(carLeaseMapper.mapCarLeaseDaoToLeaseResponse(Mockito.any(CarLeaseDao.class), Mockito.any(CarResponse.class), Mockito.any(CustomerResponse.class))).thenReturn(leaseResponse);
        Assertions.assertNotNull(carLeaseService.getLeaseAmountByCarId(1));

    }

}
