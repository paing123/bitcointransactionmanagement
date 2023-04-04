package com.anymindgroup.bitcoinmanagement.model;

import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer transId;
    
    private GregorianCalendar datetime;
    
    private Double transferAmount;

    private Double totalAmount;
     
	@Column(nullable = true)
    private String status;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

}
