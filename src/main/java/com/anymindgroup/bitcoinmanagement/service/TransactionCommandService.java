package com.anymindgroup.bitcoinmanagement.service;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;

public interface TransactionCommandService {
    
   void save(TransactionDto transaction) throws Exception;
    
}
