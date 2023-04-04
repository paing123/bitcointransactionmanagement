package com.anymindgroup.bitcoinmanagement.controller.query;


import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.service.TransactionQueryService;

@RestController
@RequestMapping("/restapi")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class TransactionQueryController {
	
    TransactionQueryService transactionQueryService;
    
    @Autowired
    public TransactionQueryController(TransactionQueryService transactionQueryService) {
        this.transactionQueryService = transactionQueryService;
    }
    
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/transactions")
    public @ResponseBody CompletableFuture<ResponseEntity> findByDatetimeBetween(@RequestBody TransactionDto transaction) throws Exception{
        
    	CompletableFuture<ResponseEntity> response = transactionQueryService.findByDatetimeBetween(transaction.getStartDatetime()
																	, transaction.getEndDatetime())
													.<ResponseEntity>thenApply(ResponseEntity::ok);
    	return response;
    }
    
    
    //to test for multitheard
//    @GetMapping(value="/transactions")
//    public @ResponseBody ResponseEntity findByDatetimeBetween(@RequestBody TransactionDto transaction) throws Exception{    	
//    	try {
//            CompletableFuture<List<TransactionDto>> t1=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//            														, transaction.getEndDatetime());
//
//            CompletableFuture<List<TransactionDto>> t2=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//					, transaction.getEndDatetime());
//
//            CompletableFuture<List<TransactionDto>> t3=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//					, transaction.getEndDatetime());
//            
//            CompletableFuture<List<TransactionDto>> t4=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//					, transaction.getEndDatetime());
//            
//            CompletableFuture<List<TransactionDto>> t5=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//					, transaction.getEndDatetime());
//
//			CompletableFuture<List<TransactionDto>> t6=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//			, transaction.getEndDatetime());
//			
//			CompletableFuture<List<TransactionDto>> t7=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//			, transaction.getEndDatetime());
//			
//			CompletableFuture<List<TransactionDto>> t8=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//			, transaction.getEndDatetime());
//			
//			CompletableFuture<List<TransactionDto>> t9=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//					, transaction.getEndDatetime());
//
//			CompletableFuture<List<TransactionDto>> t10=transactionService.findByDatetimeBetween(transaction.getStartDatetime()
//			, transaction.getEndDatetime());
//
//            CompletableFuture.allOf(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10).join();
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch(final Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
