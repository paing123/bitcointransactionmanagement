package com.anymindgroup.bitcoinmanagement.dto;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.anymindgroup.bitcoinmanagement.model.Transaction;
import com.anymindgroup.bitcoinmanagement.model.Wallet;

import lombok.Data;

@Data
public class WalletDto {
    
	private Integer walletId;
    
    private GregorianCalendar datetime;
    
    private Double balance;
    
    private Set<Transaction> transactions;
}
