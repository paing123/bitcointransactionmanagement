package com.anymindgroup.bitcoinmanagement.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;

public interface TransactionQueryService {
    
	CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime);    
}
