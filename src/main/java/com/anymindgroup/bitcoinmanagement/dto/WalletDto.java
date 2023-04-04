package com.anymindgroup.bitcoinmanagement.dto;

import java.util.GregorianCalendar;
import java.util.Set;

import com.anymindgroup.bitcoinmanagement.model.Transaction;

import lombok.Data;

@Data
public class WalletDto {
    
	private Integer walletId;
    
    private GregorianCalendar datetime;
    
    private Double balance;
    
    private Set<Transaction> transactions;
}
