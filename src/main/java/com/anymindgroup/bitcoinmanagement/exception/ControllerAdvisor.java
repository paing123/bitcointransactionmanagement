package com.anymindgroup.bitcoinmanagement.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
    		WalletNotFoundException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), 
								        		new Date(), 
												ex.getMessage(), 
												request.getDescription(false));	

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNodataFoundException(
    		NullPointerException ex, WebRequest request) {

    	ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), 
												new Date(), 
												"Data must not be null", 
												request.getDescription(false));	
        
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = TransferredValueException.class)
    public ResponseEntity<Object> handleTransferredValueException(
    		TransferredValueException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), 
								        		new Date(), 
												ex.getMessage(), 
												request.getDescription(false));	

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(TransferredDateException.class)
    public ResponseEntity<Object> handleTransferredDateException(
    		TransferredDateException ex, WebRequest request) {

    	ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), 
								        		new Date(), 
												ex.getMessage(), 
												request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
        
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), 
					        		new Date(), 
									"Data is not correct form", 
									request.getDescription(false));

      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
}
