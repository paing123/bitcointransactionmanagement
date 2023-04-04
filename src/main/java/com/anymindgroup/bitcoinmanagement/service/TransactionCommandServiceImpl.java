package com.anymindgroup.bitcoinmanagement.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anymindgroup.bitcoinmanagement.dao.TransactionRepository;
import com.anymindgroup.bitcoinmanagement.dao.WalletRepository;
import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.exception.TransferredDateException;
import com.anymindgroup.bitcoinmanagement.exception.TransferredValueException;
import com.anymindgroup.bitcoinmanagement.exception.WalletNotFoundException;
import com.anymindgroup.bitcoinmanagement.model.Transaction;
import com.anymindgroup.bitcoinmanagement.model.Wallet;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {
	
    private final TransactionRepository transactionRepo;
    
    private final WalletRepository walletRepo;
    
    private Queue<TransactionDto> queue = new ConcurrentLinkedQueue<>(); // use a concurrent queue for thread-safety

    private ExecutorService executorService = Executors.newFixedThreadPool(5); // create a thread pool with 10 threads

    @Autowired
    public TransactionCommandServiceImpl(TransactionRepository transactionRepo,WalletRepository walletRepo) {
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
}
