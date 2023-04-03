package com.anymindgroup.bitcoinmanagement.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.BeanSupplierContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.anymindgroup.bitcoinmanagement.dao.TransactionRepository;
import com.anymindgroup.bitcoinmanagement.dao.WalletRepository;
import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.exception.TransferredDateException;
import com.anymindgroup.bitcoinmanagement.exception.TransferredValueException;
import com.anymindgroup.bitcoinmanagement.exception.WalletNotFoundException;
import com.anymindgroup.bitcoinmanagement.model.Transaction;
import com.anymindgroup.bitcoinmanagement.model.Wallet;

import lombok.extern.slf4j.Slf4j;

@Service
public class TransactionServiceImpl implements TransactionService {
	
    private final TransactionRepository transactionRepo;
    
    private final WalletRepository walletRepo;
    
    private Queue<TransactionDto> queue = new ConcurrentLinkedQueue<>(); // use a concurrent queue for thread-safety

    private ExecutorService executorService = Executors.newFixedThreadPool(5); // create a thread pool with 10 threads

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepo,WalletRepository walletRepo) {
        this.transactionRepo = transactionRepo;
        this.walletRepo = walletRepo;
    }
    
    public void addToQueue(TransactionDto transaction) {
        queue.offer(transaction); // add the entity to the queue
    }

    @PostConstruct
    public void startProcessing() {
        executorService.submit(() -> {
            while (true) {
            	TransactionDto entity = queue.poll(); // get the next entity from the queue
                if (entity != null) {
                	save(entity); // process the entity
                } else {
                    try {
                        Thread.sleep(1000); // wait for 1 second if the queue is empty
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
    
    @Override
    @Async
    public CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime){
    	
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
    	
    	return CompletableFuture.completedFuture(transDtoList);
    }
    
    @Override
    @Transactional
    public void save(TransactionDto transaction) throws Exception {
    	
    	Transaction trans = new Transaction();
    	BeanUtils.copyProperties(transaction, trans);
    	
    	if(trans.getWallet() == null) {
    		Wallet wallet = new Wallet();
    		wallet.setWalletId(1);
    		trans.setWallet(wallet);
    	}
    	
    	Optional<Wallet> checkWallet = walletRepo.findById(trans.getWallet().getWalletId());
    	Double totalBalance;
    	Wallet updWallet;
    	
    	if(checkWallet.isPresent()) {
    		updWallet = checkWallet.get();
    		if(trans.getTransferAmount() <= 0 ) {
    			throw new TransferredValueException("Transferred value must not be zero or less than zero");
    		}else if(trans.getDatetime().before(Calendar.getInstance())) {
    			throw new TransferredDateException("Transferred date and time must not be less than current time");
    		}
    		totalBalance = updWallet.getBalance() + trans.getTransferAmount();
    		updWallet.setDatetime(trans.getDatetime());
    		updWallet.setBalance(totalBalance);
    		walletRepo.save(updWallet);
    	}
    	else {
    		throw new WalletNotFoundException("Wallet is not found.");
    	}
    	trans.setTotalAmount(totalBalance);
    	transactionRepo.save(trans);	
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
