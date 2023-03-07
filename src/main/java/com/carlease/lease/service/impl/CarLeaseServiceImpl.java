package com.carlease.lease.service.impl;

import com.carlease.lease.config.CarClientProperties;
import com.carlease.lease.config.CarLeaseConstant;
import com.carlease.lease.config.CustomerClientProperties;
import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.mapper.CarLeaseMapper;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.CarResponse;
import com.carlease.lease.model.response.CustomerResponse;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import com.carlease.lease.repository.CareLeaseRepository;
import com.carlease.lease.service.CarLeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarLeaseServiceImpl implements CarLeaseService {

    private final RestTemplate restTemplate;
    private final CarClientProperties carsCarClientProperties;

    private final CustomerClientProperties customerClientProperties;

    private final CarLeaseMapper carLeaseMapper;

    private final CareLeaseRepository careLeaseRepository;

    @Autowired
    public CarLeaseServiceImpl(@Qualifier("RestTemplate") RestTemplate restTemplate, CarClientProperties carsCarClientProperties, CustomerClientProperties customerClientProperties, CarLeaseMapper carLeaseMapper, CareLeaseRepository careLeaseRepository) {
        this.restTemplate = restTemplate;
        this.carsCarClientProperties = carsCarClientProperties;
        this.customerClientProperties = customerClientProperties;
        this.carLeaseMapper = carLeaseMapper;
        this.careLeaseRepository = careLeaseRepository;
    }


    private ResponseEntity<CarResponse> getCarResponseResponseEntity(String carId) {
        String uriString = UriComponentsBuilder.newInstance().scheme("http").host(carsCarClientProperties.getHostname()).path(carsCarClientProperties.getBaseUrl()).path(carId).toUriString();

        ResponseEntity<CarResponse> carResponse = restTemplate.getForEntity(uriString, CarResponse.class);
        return carResponse;
    }

    private ResponseEntity<CustomerResponse> getCustomerResponseResponseEntity(String customerId) {
        String uriString = UriComponentsBuilder.newInstance().scheme("http").host(customerClientProperties.getHostname()).path(customerClientProperties.getBaseUrl()).path(customerId).toUriString();

        ResponseEntity<CustomerResponse> customerResponse = restTemplate.getForEntity(uriString, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public LeaseResponse createNewLease(LeaseRequest leaseRequest) throws CarLeaseException {
        ResponseEntity<CarResponse> carResponse = getCarResponseResponseEntity(leaseRequest.getCarId().toString());
        ResponseEntity<CustomerResponse> customerResponse = getCustomerResponseResponseEntity(leaseRequest.getCustomerId().toString());

        if (carResponse.getBody() != null && customerResponse.getBody() != null) {
            CarResponse carResponseObject = carResponse.getBody();
            if (carResponseObject.getStatus().equalsIgnoreCase("Not-Leased")) {
                CarLeaseDao leaseDao = carLeaseMapper.mapLeaseRequestToCarLeaseDao(leaseRequest);
                leaseDao.setCarMileageAtStartOfLease(carResponseObject.getMileage());
                leaseDao.setInterestRate(CarLeaseConstant.LEASE_PERCENTAGE);
                LocalDate leaseEndDate = leaseRequest.getLeaseStartDate().plusMonths(leaseRequest.getDuration());
                leaseDao.setLeaseEndDate(leaseEndDate);
                leaseDao.setDefaultAllottedMileage(CarLeaseConstant.ALLOTTED_MILEAGE);
                Double leasePerMonth = calculateLease(leaseRequest, carResponseObject);
                leaseDao.setLeasePerMonth(leasePerMonth);
                careLeaseRepository.save(leaseDao);
                return carLeaseMapper.mapCarLeaseDaoToLeaseResponse(leaseDao, carResponseObject, customerResponse.getBody());
            }
            throw new CarLeaseException("Car is already Leased");
        } else {
            throw new CarLeaseException("Car Or Customer is not present.Please check the deatils");
        }


    }

    @Override
    public List<LeaseCalculationResponse> getLeaseAmountByCustomerId(Integer customerId) throws CustomerNotFoundException {
        ResponseEntity<CustomerResponse> customerResponse = getCustomerResponseResponseEntity(customerId.toString());
        if (customerResponse.getBody() != null) {
           return careLeaseRepository.findByCustomerId(customerId).stream().map(carLeaseDao -> {
                return carLeaseMapper.mapCarLeaseDaoToLeaseCalcResponse(carLeaseDao);
            }).collect(Collectors.toList());

        }
        else{
            throw new CustomerNotFoundException("Customer is not present");
        }
    }

    private Double calculateLease(LeaseRequest leaseRequest, CarResponse carResponseObject) {
        Long mileage = CarLeaseConstant.ALLOTTED_MILEAGE;
        Integer duration = leaseRequest.getDuration();
        Double interestRate = CarLeaseConstant.LEASE_PERCENTAGE;
        Double netPrice = carResponseObject.getNetPrice();
        Double amount = (((mileage / 12) * duration) / netPrice) + (((interestRate / 100) * netPrice) / 12);
        BigDecimal bigDecimal = new BigDecimal(Double.toString(amount));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
