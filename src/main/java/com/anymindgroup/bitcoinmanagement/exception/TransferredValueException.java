package com.anymindgroup.bitcoinmanagement.exception;

public class TransferredValueException extends Exception { 
    
	private static final long serialVersionUID = 1L;

	public TransferredValueException(String message) {
        super(message);
    }
}