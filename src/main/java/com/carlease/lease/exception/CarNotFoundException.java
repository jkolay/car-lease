package com.carlease.lease.exception;

/**
 * Custom Exception for Car Not Found Case
 */
public class CarNotFoundException extends Exception{
    public CarNotFoundException(String message) {
        super(message);
    }

}
