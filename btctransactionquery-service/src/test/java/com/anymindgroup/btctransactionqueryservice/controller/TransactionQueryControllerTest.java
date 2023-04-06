package com.anymindgroup.btctransactionqueryservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.anymindgroup.btctransactionqueryservice.dto.TransactionDto;
import com.anymindgroup.btctransactionqueryservice.service.TransactionQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionQueryController.class)
public class TransactionQueryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransactionQueryService transactionQueryService;

	@DisplayName("JUnit test for findByDatetimeBetween and return ok status")
	@Test
	public void findByDatetimeBetweenTest_thenReturnOK() throws Exception {
		GregorianCalendar startDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		GregorianCalendar endDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

		CompletableFuture<List<TransactionDto>> response = CompletableFuture.completedFuture(new ArrayList<>());

		Mockito.when(transactionQueryService.findByDatetimeBetween(startDateTime, endDateTime)).thenReturn(response);

		TransactionDto transaction = new TransactionDto();
		transaction.setStartDatetime(startDateTime);
		transaction.setEndDatetime(endDateTime);

		String requestBody = objectMapper.writeValueAsString(transaction);

		mockMvc.perform(get("http://localhost:8080/restapi/btctransquery-service/transactions")
				.contentType("application/json").content(requestBody)).andExpect(status().isOk());
	}

	@DisplayName("JUnit test for findByDatetimeBetween and return client bad request status")
	@Test
	public void findByDatetimeBetweenTest_thenReturnClientBadRequest() throws Exception {
		GregorianCalendar startDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		GregorianCalendar endDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

		CompletableFuture<List<TransactionDto>> response = CompletableFuture.completedFuture(new ArrayList<>());

		Mockito.when(transactionQueryService.findByDatetimeBetween(startDateTime, endDateTime)).thenReturn(response);

		TransactionDto transaction = new TransactionDto();
		transaction.setStartDatetime(startDateTime);
		transaction.setEndDatetime(endDateTime);

		String requestBody = objectMapper.writeValueAsString(transaction);

		mockMvc.perform(get("http://localhost:8080/restapi/btctransquery-service/transactions").content(requestBody))
				.andExpect(status().isBadRequest());
	}

	@DisplayName("JUnit test for findByDatetimeBetween and return client not found status")
	@Test
	public void findByDatetimeBetweenTest_thenReturnClientNotFound() throws Exception {
		GregorianCalendar startDateTime = null;
		GregorianCalendar endDateTime = null;

		CompletableFuture<List<TransactionDto>> response = null;

		Mockito.when(transactionQueryService.findByDatetimeBetween(startDateTime, endDateTime)).thenReturn(response);

		TransactionDto transaction = new TransactionDto();

		String requestBody = objectMapper.writeValueAsString(transaction);

		mockMvc.perform(get("http://localhost:8080/restapi/btctransquery-service/transactions")
				.contentType("application/json").content(requestBody)).andExpect(status().isNotFound());
	}
}
