package com.carlease.lease.services;

import com.carlease.lease.leaseDto.CarResponse;
import com.carlease.lease.leaseDto.LeaseRequest;
import com.carlease.lease.leaseDto.LeaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CarLeaseServicesImpl implements CarLeaseServices {

    private final RestTemplate restTemplate;
    private final CarClientProperties carsCarClientProperties;

    @Autowired
    public CarLeaseServicesImpl(@Qualifier("CarsRestTemplate") RestTemplate restTemplate, CarClientProperties carsCarClientProperties) {
        this.restTemplate = restTemplate;
        this.carsCarClientProperties = carsCarClientProperties;
    }
    @Override
    public LeaseResponse calculateCarLease(LeaseRequest leaseRequest) {

        LeaseResponse leaseResponse = null;
        String uriString = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(carsCarClientProperties.getHostname())
                .path(carsCarClientProperties.getBaseUrl())
                .path(leaseRequest.getCarId().toString())
                .toUriString();

        ResponseEntity<CarResponse> response
                = restTemplate.getForEntity(uriString , CarResponse.class);
        if(leaseRequest.getCarId().equals(response.getBody().getCarId()))
        {
            leaseResponse= new LeaseResponse();
            leaseResponse.setCarId (response.getBody().getCarId());
            return leaseResponse;
        }
        return new LeaseResponse();
    }
}
