package com.carlease.lease.exception;

public class CarLeaseException extends Exception {
    public CarLeaseException() {
        super();
    }

    public CarLeaseException(String message) {
        super(message);
    }

    public CarLeaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarLeaseException(Throwable cause) {
        super(cause);
    }

    protected CarLeaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
