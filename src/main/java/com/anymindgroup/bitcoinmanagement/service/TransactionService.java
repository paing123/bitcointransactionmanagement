package com.anymindgroup.bitcoinmanagement.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.model.Transaction;

public interface TransactionService {
    
    List<TransactionDto> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime);
    
    TransactionDto save(TransactionDto transaction) throws Exception;
    
}
