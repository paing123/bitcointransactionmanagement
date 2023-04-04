package com.anymindgroup.bitcoinmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anymindgroup.bitcoinmanagement.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	
}
