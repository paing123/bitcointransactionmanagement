package com.anymindgroup.bitcoinmanagement.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.model.Transaction;

public interface TransactionService {
    
	CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime);
    
    void save(TransactionDto transaction) throws Exception;
    
}
