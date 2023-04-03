package com.anymindgroup.bitcoinmanagement.dto;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.anymindgroup.bitcoinmanagement.model.Wallet;
import com.fasterxml.jackson.annotation.JsonFormat;

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
