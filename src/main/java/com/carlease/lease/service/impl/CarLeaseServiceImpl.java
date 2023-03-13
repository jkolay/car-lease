package com.carlease.lease.service.impl;

import com.carlease.lease.client.CarClientProperties;
import com.carlease.lease.client.CustomerClientProperties;
import com.carlease.lease.config.CarLeaseConstant;
import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.mapper.CarLeaseMapper;
import com.carlease.lease.model.LoggedInUser;
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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The service impl class for implementation of lease service method
 */
@Service
public class CarLeaseServiceImpl implements CarLeaseService {

    private final RestTemplate restTemplate;
    private final CarClientProperties carsCarClientProperties;

    private final CustomerClientProperties customerClientProperties;

    private final CarLeaseMapper carLeaseMapper;

    private final CareLeaseRepository careLeaseRepository;

    LoggedInUser loggedInUser;
    @Autowired
    public CarLeaseServiceImpl(@Qualifier("RestTemplate") RestTemplate restTemplate, CarClientProperties carsCarClientProperties, CustomerClientProperties customerClientProperties, CarLeaseMapper carLeaseMapper, CareLeaseRepository careLeaseRepository) {
        this.restTemplate = restTemplate;
        this.carsCarClientProperties = carsCarClientProperties;
        this.customerClientProperties = customerClientProperties;
        this.carLeaseMapper = carLeaseMapper;
        this.careLeaseRepository = careLeaseRepository;
        loggedInUser=new LoggedInUser();
    }

    /**
     * This method retrieve car details from car service with the car id
     *
     * @param carId id of the car
     * @return
     */
    private ResponseEntity<CarResponse> getCarResponseResponseEntity(String carId, HttpEntity jwtEntity) {
        String uriString = UriComponentsBuilder.newInstance().scheme(CarLeaseConstant.HTTP).host(carsCarClientProperties.getHostname()).port(carsCarClientProperties.getPort()).path(carsCarClientProperties.getBaseUrl()).path("view/"+carId).toUriString();

        ResponseEntity<CarResponse> carResponse = restTemplate.exchange(uriString, HttpMethod.GET, jwtEntity, CarResponse.class);
        return carResponse;
    }
    /**
     * This method retrieves customer details from customer service by customer id
     *
     * @param customerId the customer id
     * @return
     */
    private ResponseEntity<CustomerResponse> getCustomerResponseEntity(String customerId, HttpEntity<String> jwtEntity) {
        String uriString = UriComponentsBuilder.newInstance().scheme(CarLeaseConstant.HTTP).host(customerClientProperties.getHostname()).port(customerClientProperties.getPort()).path(customerClientProperties.getBaseUrl()).path("view/"+customerId).toUriString();

        ResponseEntity<CustomerResponse> customerResponse = restTemplate.exchange(uriString, HttpMethod.GET, jwtEntity, CustomerResponse.class);
        return customerResponse;
    }

    /**
     * This method create new lease between a car and the customer. After retrieving all the necessary details from
     * car and customer service , it calculates the monthly lease and store it in lease_per_month column in db.
     * it also updates the car and customer status as leased after crating the new lease.
     *
     * @param leaseRequest the lease request model
     * @return
     * @throws CarLeaseException
     */
    @Override
    public LeaseResponse createNewLease(LeaseRequest leaseRequest) throws CarLeaseException {
        ResponseEntity<CarResponse> carResponse = getCarResponseResponseEntity(leaseRequest.getCarId().toString(), jwtEntityResponse(carsCarClientProperties.getPort()));
        ResponseEntity<CustomerResponse> customerResponse = getCustomerResponseEntity(leaseRequest.getCustomerId().toString(), jwtEntityResponse(customerClientProperties.getPort()));

        if (carResponse.getBody() != null && customerResponse.getBody() != null) {
            CarResponse carResponseObject = carResponse.getBody();
            CustomerResponse customerResponseObj = customerResponse.getBody();
            CarLeaseDao carLeaseDao=careLeaseRepository.findByCarId(carResponseObject.getCarId());
            if (carLeaseDao!=null ) {
                throw new CarLeaseException("Car is already Leased");
                }
            CarLeaseDao leaseDao = carLeaseMapper.mapLeaseRequestToCarLeaseDao(leaseRequest);
            leaseDao.setCarMileageAtStartOfLease(carResponseObject.getMileage());
            leaseDao.setInterestRate(CarLeaseConstant.LEASE_PERCENTAGE);
            LocalDate leaseEndDate = leaseRequest.getLeaseStartDate().plusMonths(leaseRequest.getDuration());
            leaseDao.setLeaseEndDate(leaseEndDate);
            leaseDao.setDefaultAllottedMileage(CarLeaseConstant.ALLOTTED_MILEAGE);
            Double leasePerMonth = calculateLease(leaseRequest, carResponseObject);
            leaseDao.setLeasePerMonth(leasePerMonth);
            careLeaseRepository.save(leaseDao);
            return carLeaseMapper.mapCarLeaseDaoToLeaseResponse(leaseDao, carResponseObject, customerResponseObj);

        } else {
            throw new CarLeaseException("Car Or Customer is not present.Please check the details");
        }


    }

    /**
     * this is the implementation method to retrieve lease amount by customer details of the lease
     * @param customerId the customer id
     * @return the list of list objects
     * @throws CustomerNotFoundException
     */
    @Override
    public List<LeaseCalculationResponse> getLeaseAmountByCustomerId(Integer customerId) throws CustomerNotFoundException {
        ResponseEntity<CustomerResponse> customerResponse = getCustomerResponseEntity(customerId.toString(), jwtEntityResponse(carsCarClientProperties.getPort()));
        if (customerResponse.getBody() != null) {
            return careLeaseRepository.findByCustomerId(customerId).stream().map(carLeaseDao ->
                 carLeaseMapper.mapCarLeaseDaoToLeaseCalcResponse(carLeaseDao)
            ).collect(Collectors.toList());

        } else {
            throw new CustomerNotFoundException("Customer is not present");
        }
    }

    public HttpEntity<String> jwtEntityResponse(Integer port)
    {
        String uriString = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/user")
                .toUriString();

        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<>(authenticationHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uriString, HttpMethod.GET, authenticationEntity, String.class);

        String token = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        String xsrf = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0).substring(11, 47);
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", token);
        headers.set("X-XSRF-TOKEN", xsrf);
        HttpEntity<String> jwtEntity = new HttpEntity<>(headers);
        return jwtEntity;
    }

    /**
     * This is the implementation method to retrieve lease details of a leased car
     * @param carId the car id
     * @return the lease details of the car
     * @throws CarNotFoundException
     */
    @Override
    public LeaseResponse getLeaseAmountByCarId(Integer carId) throws CarNotFoundException {

        ResponseEntity<CarResponse> carResponse = getCarResponseResponseEntity(carId.toString(), jwtEntityResponse(carsCarClientProperties.getPort()));
        CarResponse carResponseObj = carResponse.getBody();
        if (carResponseObj != null) {
            CarLeaseDao carLeaseDao = careLeaseRepository.findByCarId(carId);
            ResponseEntity<CustomerResponse> customerResponseEntity = getCustomerResponseEntity(carLeaseDao.getCustomerId().toString(), jwtEntityResponse(carsCarClientProperties.getPort()));
            return carLeaseMapper.mapCarLeaseDaoToLeaseResponse(careLeaseRepository.findByCarId(carId), carResponseObj, customerResponseEntity.getBody());
        }
        throw new CarNotFoundException("Car id is not valid");
    }

    /**
     * This method calculates the lease amount of the car and return the amount .
     * @param leaseRequest the lease request details which consist of the car id,the duration of lease
     * @param carResponseObject the car object
     * @return
     */
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

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CarLeaseConstant.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(CarLeaseConstant.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        loggedInUser.getUser();
        loggedInUser.getPassword();
        headers.set(CarLeaseConstant.AUTHORIZATION, CarLeaseConstant.BASIC + HttpHeaders.encodeBasicAuth(loggedInUser.getUser(), loggedInUser.getPassword(), StandardCharsets.UTF_8));
        return headers;
    }
    public void setValue(String username, String pwd) {
        loggedInUser.setUser(username);
        loggedInUser.setPassword(pwd);
    }
}
