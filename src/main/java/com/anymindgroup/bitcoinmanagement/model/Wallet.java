package com.anymindgroup.bitcoinmanagement.model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer walletId;
    
    private GregorianCalendar datetime;
    
    private Double balance;

}
