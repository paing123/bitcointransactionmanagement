package com.anymindgroup.bitcoinmanagement.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.service.TransactionCommandService;

@RestController
@RequestMapping("/restapi")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class TransactionCommandController {
	
    TransactionCommandService transactionCommandService;
    
    @Autowired
    public TransactionCommandController(TransactionCommandService transactionCommandService) {
        this.transactionCommandService = transactionCommandService;
    }
             
    @SuppressWarnings("rawtypes")
	@PostMapping(value="/transactions")
	public @ResponseBody ResponseEntity saveTransaction(@RequestBody TransactionDto transaction) throws Exception{  	
    	
    	transactionCommandService.save(transaction);
    	return ResponseEntity.status(HttpStatus.OK).build();
	}
}
