package com.anymindgroup.bitcoinmanagement.dto;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.anymindgroup.bitcoinmanagement.model.Wallet;

import lombok.Data;

@Data
public class TransactionDto {
    
    private Integer transId;
    
    private GregorianCalendar startDatetime;

    private GregorianCalendar endDatetime;
    
    private GregorianCalendar datetime;
    
    @NotEmpty(message = "Transfered Amount should not be empty.")
    @Min(value = 1, message = "Transfered Amount should not be less than 1.")
    private Double transferAmount;
    
    @Min(value = 0, message = "Total Amount should not be less than 0.")
    private Double totalAmount;
    
    private Wallet wallet;
     
    private String status;
}
