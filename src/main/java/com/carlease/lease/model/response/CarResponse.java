package com.carlease.lease.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Car Response Modle class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private Integer carId;

    private String make;

    private String model;

    private String version;

    private Integer numberOfDoors;

    private Double co2Emission;

    private Double netPrice;

    private Double grossPrice;
    private Long mileage;


}
