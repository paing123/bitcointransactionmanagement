package com.anymindgroup.bitcoinmanagement.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anymindgroup.bitcoinmanagement.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	List<Transaction> findByDatetimeBetween(GregorianCalendar creationDateTime, GregorianCalendar endDateTime);
}
