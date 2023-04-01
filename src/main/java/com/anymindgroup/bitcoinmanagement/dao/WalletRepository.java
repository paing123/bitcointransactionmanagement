package com.anymindgroup.bitcoinmanagement.dao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.model.Transaction;
import com.anymindgroup.bitcoinmanagement.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	
}
