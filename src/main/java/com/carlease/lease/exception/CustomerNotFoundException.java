package com.carlease.lease.exception;

/**
 * Custom Exception for Customer Not Found
 */
public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
