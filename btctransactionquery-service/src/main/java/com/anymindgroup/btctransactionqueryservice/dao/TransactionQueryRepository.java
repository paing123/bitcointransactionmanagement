package com.anymindgroup.btctransactionqueryservice.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anymindgroup.btctransactionqueryservice.model.Transaction;


@Repository
public interface TransactionQueryRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByDatetimeBetween(GregorianCalendar creationDateTime, GregorianCalendar endDateTime);

	Transaction findByDatetimeBefore(GregorianCalendar creationDateTime);
}
