package com.anymindgroup.btctransactioncommandservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anymindgroup.btctransactioncommandservice.model.Transaction;

@Repository
public interface TransactionCommandRepository extends JpaRepository<Transaction, Long> {
	
	Transaction findFirstByOrderByTransIdDesc();
}
