package com.carlease.lease.leaseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;


@Data
public class LeaseResponse {


    private Long leaseId;
    private Long customerId;
    private Integer carId;
    private Integer duration;
    private Long interestRate;
    private Integer mileage;
}
