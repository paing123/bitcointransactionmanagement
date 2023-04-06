package com.anymindgroup.btctransactionqueryservice.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anymindgroup.btctransactionqueryservice.dao.TransactionQueryRepository;
import com.anymindgroup.btctransactionqueryservice.dto.TransactionDto;
import com.anymindgroup.btctransactionqueryservice.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements TransactionQueryService {

	private final TransactionQueryRepository transactionRepo;

	@Override
	@Async
	public CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime,
			GregorianCalendar endDatetime) {

		List<Transaction> transList = transactionRepo.findByDatetimeBetween(startDatetime, endDatetime);
		List<TransactionDto> transDtoList = new ArrayList<>();

		if (!transList.isEmpty()) {

			int index = 1;

			if (transList.size() == 1) {
				Transaction trans = transactionRepo.findByDatetimeBefore(transList.get(0).getDatetime());
				// check previous datetime of select transaction
				if (trans == null) {
					index = 0;
					startDatetime = transList.get(0).getDatetime();
					startDatetime.setTimeZone(TimeZone.getTimeZone("UTC"));
				} else {
					transList.add(0, trans);
				}
			} else {
				startDatetime = transList.get(0).getDatetime();
				startDatetime.setTimeZone(TimeZone.getTimeZone("UTC"));
			}

			List<GregorianCalendar> endOfHourIntervals = this.getEndOfHourIntervals(startDatetime, endDatetime);

			for (GregorianCalendar eachHour : endOfHourIntervals) {

				Transaction transaction = transList.get(index);
				TransactionDto newTrans = new TransactionDto();
				newTrans.setDatetime(eachHour);

				if (eachHour.before(transaction.getDatetime()) && index > 0) {
					newTrans.setTotalAmount(transList.get(index - 1).getTotalAmount());
				} else {
					newTrans.setTotalAmount(transList.get(index).getTotalAmount());

					if (index < transList.size() - 1) {
						index++;
					}
				}
				transDtoList.add(newTrans);
			}
		}

		return CompletableFuture.completedFuture(transDtoList);
	}

	private List<GregorianCalendar> getEndOfHourIntervals(GregorianCalendar startDate, GregorianCalendar endDate) {
		List<GregorianCalendar> intervals = new ArrayList<>();

		// Set the start date to the end of the hour
		startDate.set(Calendar.MINUTE, 59);
		startDate.set(Calendar.SECOND, 59);
		startDate.set(Calendar.MILLISECOND, 1000);

		// Loop through the dates between the start and end dates
		while (startDate.getTime().before(endDate.getTime())) {
			intervals.add((GregorianCalendar) startDate.clone());
			startDate.add(Calendar.HOUR_OF_DAY, 1); // Increment the calendar by 1 hour
		}

		return intervals;
	}

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	@Transactional
	public void receivedMessage(Transaction transaction) {
		transactionRepo.save(transaction);
	}

}
