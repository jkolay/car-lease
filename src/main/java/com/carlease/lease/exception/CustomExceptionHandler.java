package com.carlease.lease.exception;


import com.carlease.lease.config.CarLeaseErrorCodeConfig;
import com.carlease.lease.model.error.ErrorSeverityLevelCodeType;
import com.carlease.lease.model.error.LeaseErrorModel;
import com.carlease.lease.model.error.LeaseRequestErrorModel;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the custom exception handler class
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        final LeaseRequestErrorModel error = new LeaseRequestErrorModel(errors, CarLeaseErrorCodeConfig.INVALID_INPUT, ErrorSeverityLevelCodeType.ERROR.ERROR);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.INVALID_INPUT, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

   

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleCarNotFoundException(CarNotFoundException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.INVALID_INPUT, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(CarLeaseException.class)
    @ResponseBody
    public ResponseEntity<Object> handleCarLeaseException(CarLeaseException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.INVALID_INPUT, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidDataAccessApiUsageException.class})
    @ResponseBody
    public ResponseEntity<Object> handleArgumentException(Exception ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.INVALID_INPUT, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            message = CarLeaseErrorCodeConfig.DB_ERROR;
        }

        final LeaseErrorModel error = new LeaseErrorModel(message, CarLeaseErrorCodeConfig.DB_ERROR, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public final ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.DB_ERROR, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.BAD_CREDENTIALS, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        final LeaseErrorModel error = new LeaseErrorModel("Authentication failed", CarLeaseErrorCodeConfig.UNAUTHORIZED, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.INTERNAL_ERROR, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        final LeaseErrorModel error = new LeaseErrorModel(ex.getMessage(), CarLeaseErrorCodeConfig.GLOBAL_ERROR, ErrorSeverityLevelCodeType.ERROR);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
