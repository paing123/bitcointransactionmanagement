package com.anymindgroup.btctransactionqueryservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anymindgroup.btctransactionqueryservice.dto.TransactionDto;
import com.anymindgroup.btctransactionqueryservice.service.TransactionQueryService;

@RestController
@RequestMapping("/restapi/btctransquery-service")
public class TransactionQueryController {

	TransactionQueryService transactionQueryService;

	@Autowired
	public TransactionQueryController(TransactionQueryService transactionQueryService) {
		this.transactionQueryService = transactionQueryService;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/transactions")
	public @ResponseBody CompletableFuture<ResponseEntity> findByDatetimeBetween(
			@RequestBody TransactionDto transaction) throws Exception {

		CompletableFuture<ResponseEntity> response = transactionQueryService
				.findByDatetimeBetween(transaction.getStartDatetime(), transaction.getEndDatetime())
				.<ResponseEntity>thenApply(ResponseEntity::ok);
		return response;
	}

}
