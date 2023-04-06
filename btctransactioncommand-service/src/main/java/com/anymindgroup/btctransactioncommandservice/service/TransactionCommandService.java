package com.anymindgroup.btctransactioncommandservice.service;

import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;


public interface TransactionCommandService {
    
   TransactionDto save(TransactionDto transaction) throws Exception;
    
}
