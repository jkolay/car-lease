package com.carlease.lease.model.response;

import java.time.LocalDate;
import lombok.Data;

/** Laese response model to show response after new lease craetion */
@Data
public class LeaseResponse {
  private Integer leaseId;
  private Integer duration;
  private LocalDate leaseStartDate;
  private LocalDate leaseEndDate;
  private Double interestRate;
  private Double defaultAllottedMileage;
  private Double leasePerMonth;
  private CustomerResponse customer;
  private CarResponse car;
}
