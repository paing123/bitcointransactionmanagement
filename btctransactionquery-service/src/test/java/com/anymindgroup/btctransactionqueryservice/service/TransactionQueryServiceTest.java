package com.anymindgroup.btctransactionqueryservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.anymindgroup.btctransactionqueryservice.dao.TransactionQueryRepository;
import com.anymindgroup.btctransactionqueryservice.dto.TransactionDto;
import com.anymindgroup.btctransactionqueryservice.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class TransactionQueryServiceTest {

	@Mock
	private TransactionQueryRepository transactionQueryRepo;

	@InjectMocks
	private TransactionQueryServiceImpl transactionQueryService;

	@DisplayName("JUnit test for findByDatetimeBetween(startDatetime, endDatetime) and return empty list")
	@Test
	public void findByDatetimeBetween_thenReturnEmptyList() throws Exception {
		GregorianCalendar startDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		GregorianCalendar endDateTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

		given(transactionQueryRepo.findByDatetimeBetween(startDateTime, endDateTime))
									.willReturn(Collections.emptyList());

		List<TransactionDto> transList = transactionQueryService.findByDatetimeBetween(startDateTime, endDateTime)
				.get();

		assertThat(transList).isEmpty();
		;
		assertThat(transList.size()).isEqualTo(0);
	}

	@DisplayName("JUnit test for findByDatetimeBetween(startDatetime, endDatetime) and return result list")
	@Test
	public void findByDatetimeBetween_thenReturnResultList() throws Exception {
		GregorianCalendar startDateTime = new GregorianCalendar(2023, 2, 1);
		GregorianCalendar endDateTime = new GregorianCalendar(2023, 4, 1);

		Transaction transaction1 = new Transaction();
		Transaction transaction2 = new Transaction();
		List<Transaction> transList = new ArrayList<>();
		transaction1.setDatetime(startDateTime);
		transaction1.setTotalAmount(1000.00);
		transaction2.setDatetime(startDateTime);
		transaction2.setTotalAmount(1000.00);
		transList.add(transaction1);
		transList.add(transaction2);

		given(transactionQueryRepo.findByDatetimeBetween(startDateTime, endDateTime)).willReturn(transList);

		List<TransactionDto> transResultList = transactionQueryService.findByDatetimeBetween(startDateTime, endDateTime)
																		.get();

		assertThat(transResultList.size()).isGreaterThan(0);
	}

}
