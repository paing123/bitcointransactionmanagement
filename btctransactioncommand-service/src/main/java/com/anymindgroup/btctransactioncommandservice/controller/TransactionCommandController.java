package com.anymindgroup.btctransactioncommandservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;
import com.anymindgroup.btctransactioncommandservice.service.TransactionCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restapi/btctranscommand-service")
public class TransactionCommandController {

	private final TransactionCommandService transactionCommandService;

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/transactions")
	public @ResponseBody ResponseEntity saveTransaction(@RequestBody TransactionDto transaction) throws Exception {

		transactionCommandService.save(transaction);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
