package com.anymindgroup.btctransactioncommandservice.service;

import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;


public interface TransactionCommandService {
    
   void save(TransactionDto transaction) throws Exception;
    
}
