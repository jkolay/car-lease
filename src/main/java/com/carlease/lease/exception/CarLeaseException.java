package com.carlease.lease.exception;

/**
 * Custom Exception class for Car Lease Service
 */
public class CarLeaseException extends Exception {
    public CarLeaseException() {
    }

    public CarLeaseException(String message) {
        super(message);
    }
}
