package com.anymindgroup.bitcoinmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TransferredValueException extends Exception { 
    
	private static final long serialVersionUID = 1L;

	public TransferredValueException(String message) {
        super(message);
    }
}