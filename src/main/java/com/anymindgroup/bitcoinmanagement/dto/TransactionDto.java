package com.anymindgroup.bitcoinmanagement.dto;

import java.util.GregorianCalendar;

import com.anymindgroup.bitcoinmanagement.model.Wallet;

import lombok.Data;

@Data
public class TransactionDto {
    
    private Integer transId;

    private GregorianCalendar startDatetime;

    private GregorianCalendar endDatetime;
    
    private GregorianCalendar datetime;
    
    private Double transferAmount;
    
    private Double totalAmount;
    
    private Wallet wallet;
     
    private String status;
}
