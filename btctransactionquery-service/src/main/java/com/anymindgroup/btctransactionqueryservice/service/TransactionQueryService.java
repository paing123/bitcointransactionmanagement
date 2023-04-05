package com.anymindgroup.btctransactionqueryservice.service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.anymindgroup.btctransactionqueryservice.dto.TransactionDto;

public interface TransactionQueryService {
    
	CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime);    
}
