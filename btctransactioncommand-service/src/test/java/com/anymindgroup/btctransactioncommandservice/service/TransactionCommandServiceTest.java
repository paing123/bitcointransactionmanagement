package com.anymindgroup.btctransactioncommandservice.service;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.anymindgroup.btctransactioncommandservice.dao.TransactionCommandRepository;
import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;
import com.anymindgroup.btctransactioncommandservice.exception.TransferredDateException;
import com.anymindgroup.btctransactioncommandservice.exception.TransferredValueException;
import com.anymindgroup.btctransactioncommandservice.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class TransactionCommandServiceTest {

	@Mock
	private TransactionCommandRepository transactionCommandRepo;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private TransactionCommandServiceImpl transactionCommandService;

	@DisplayName("JUnit test for saveTransaction and return transId 1")
	@Test
	public void saveTransaction_ReturnTransId1() throws Exception {
		GregorianCalendar datetime = new GregorianCalendar(2023, 5, 6);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setDatetime(datetime);
		transactionDto.setTransferAmount(100.00);
		transactionDto.setTotalAmount(100.00);

		Transaction transaction = new Transaction();
		Transaction returnTransaction = new Transaction();
		BeanUtils.copyProperties(transactionDto, transaction);
		BeanUtils.copyProperties(transactionDto, returnTransaction);
		returnTransaction.setTransId(1l);

		given(transactionCommandRepo.save(transaction)).willReturn(returnTransaction);
		transactionDto = transactionCommandService.save(transactionDto);

		assertThat(transactionDto.getTransId()).isEqualTo(1l);
	}

	@DisplayName("JUnit test for saveTransaction method which throws TransferredDateException")
	@Test
	public void saveTransaction_ThrowsTransferredDateException() {

		TransactionDto transactionDto = new TransactionDto();
		GregorianCalendar datetime = new GregorianCalendar(2022, 4, 5);
		transactionDto.setDatetime(datetime);
		transactionDto.setTransferAmount(100.00);

		// when - action or the behaviour that we are going test
		assertThrows(TransferredDateException.class, () -> {
			transactionCommandService.save(transactionDto);
		});
	}

	@DisplayName("JUnit test for saveTransaction method which throws TransferredValueException")
	@Test
	public void saveTransaction_ThrowsTransferredValueException() {

		TransactionDto transactionDto = new TransactionDto();
		GregorianCalendar datetime = new GregorianCalendar(2023, 6, 5);
		transactionDto.setDatetime(datetime);
		transactionDto.setTransferAmount(0.00);

		// when - action or the behaviour that we are going test
		assertThrows(TransferredValueException.class, () -> {
			transactionCommandService.save(transactionDto);
		});
	}

}
