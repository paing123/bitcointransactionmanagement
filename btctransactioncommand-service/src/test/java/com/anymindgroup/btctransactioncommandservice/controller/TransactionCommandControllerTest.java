package com.anymindgroup.btctransactioncommandservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;
import com.anymindgroup.btctransactioncommandservice.service.TransactionCommandService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionCommandController.class)
public class TransactionCommandControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransactionCommandService transactionCommandService;

	@DisplayName("JUnit test for save transaction and return ok status")
	@Test
	public void saveTransaction_thenReturnOK() throws Exception {
		GregorianCalendar dateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		TransactionDto transaction = new TransactionDto();
		transaction.setDatetime(dateTime);
		transaction.setTransferAmount(500.00);

		String requestBody = objectMapper.writeValueAsString(transaction);

		mockMvc.perform(post("http://localhost:8081/restapi/btctranscommand-service/transactions")
				.contentType("application/json").content(requestBody)).andExpect(status().isOk());
	}
}
