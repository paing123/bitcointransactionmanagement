package com.anymindgroup.btctransactioncommandservice.exception;

public class TransferredDateException extends RuntimeException { 
    
	private static final long serialVersionUID = 1L;

	public TransferredDateException(String message) {
        super(message);
    }
}