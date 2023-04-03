package com.anymindgroup.bitcoinmanagement.model;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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
