package com.anymindgroup.bitcoinmanagement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.BeanSupplierContext;
import org.springframework.stereotype.Service;

import com.anymindgroup.bitcoinmanagement.dao.TransactionRepository;
import com.anymindgroup.bitcoinmanagement.dao.WalletRepository;
import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.model.Transaction;
import com.anymindgroup.bitcoinmanagement.model.Wallet;

@Service
public class TransactionServiceImpl implements TransactionService {
	
    private final TransactionRepository transactionRepo;
    
    private final WalletRepository walletRepo;
    
    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepo,WalletRepository walletRepo) {
        this.transactionRepo = transactionRepo;
        this.walletRepo = walletRepo;
    }
    
    @Override
    public List<TransactionDto> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime){
    	
    	List<Transaction> transList = transactionRepo.findByDatetimeBetween(startDatetime, endDatetime);
    	List<TransactionDto> transDtoList = new ArrayList<>();
    	
    	List<GregorianCalendar> endOfHourIntervals = this.getEndOfHourIntervals(startDatetime, endDatetime);
    	
    	int index = transList.size() == 1 ? 0 : 1;
    	for (GregorianCalendar eachHour : endOfHourIntervals) {
    		
    		Transaction transaction = transList.get(index);
    		TransactionDto newTrans = new TransactionDto();
			newTrans.setDatetime(eachHour);
			
			if(eachHour.before(transaction.getDatetime())) {	
				newTrans.setTotalAmount(transList.get(index-1).getTotalAmount());
			}else {
				newTrans.setTotalAmount(transList.get(index).getTotalAmount());
				
				if(index < transList.size() - 1 ) {
					index++;
				}
			}	
			transDtoList.add(newTrans);
		}
    	
    	return transDtoList;
    }
    
    @Transactional
    @Override
    public TransactionDto save(TransactionDto transaction) throws Exception {
    	
    	Transaction trans = new Transaction();
    	BeanUtils.copyProperties(transaction, trans);
    	
    	if(trans.getWallet() == null) {
    		Wallet wallet = new Wallet();
    		wallet.setWalletId(1);
    		trans.setWallet(wallet);
    	}
    	
    	Optional<Wallet> checkWallet = walletRepo.findById(trans.getWallet().getWalletId());
    	Double totalBalance;
    	
    	if(checkWallet.isPresent()) {
    		Wallet updWallet = checkWallet.get();
    		totalBalance = updWallet.getBalance() + trans.getTransferAmount();
    		updWallet.setDatetime(trans.getDatetime());
    		updWallet.setBalance(totalBalance);
    		walletRepo.save(updWallet);
    	}
    	else {
    		throw new Exception("Wallet save must be first.");
    	}
    	
    	trans.setTotalAmount(totalBalance);
    	transactionRepo.save(trans);
    	transaction.setTransId(trans.getTransId());
    	transaction.setTotalAmount(totalBalance);
    	
        return transaction;
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
    
}
