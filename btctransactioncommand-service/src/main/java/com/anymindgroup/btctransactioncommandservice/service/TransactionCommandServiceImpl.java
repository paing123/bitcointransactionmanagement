package com.anymindgroup.btctransactioncommandservice.service;

import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anymindgroup.btctransactioncommandservice.dao.TransactionCommandRepository;
import com.anymindgroup.btctransactioncommandservice.dto.TransactionDto;
import com.anymindgroup.btctransactioncommandservice.exception.TransferredDateException;
import com.anymindgroup.btctransactioncommandservice.exception.TransferredValueException;
import com.anymindgroup.btctransactioncommandservice.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionCommandServiceImpl implements TransactionCommandService {
	
	private final RabbitTemplate rabbitTemplate;
	
    private final TransactionCommandRepository transactionRepo;
    
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;
    
    private Queue<TransactionDto> queue = new ConcurrentLinkedQueue<>(); // use a concurrent queue for thread-safety

    private ExecutorService executorService = Executors.newFixedThreadPool(10); // create a thread pool with 10 threads
    
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
    public void save(TransactionDto transactionDto) throws Exception {
    	
    	Transaction newTransaction = new Transaction();
    	BeanUtils.copyProperties(transactionDto, newTransaction);
    	
    	if(newTransaction.getTransferAmount() <= 0 ) {
			throw new TransferredValueException("Transferred value must not be zero or less than zero");
		}else if(newTransaction.getDatetime().before(Calendar.getInstance())) {
			throw new TransferredDateException("Transferred date and time must not be less than current time");
		}
    	
    	Transaction transaction = transactionRepo.findFirstByOrderByTransIdDesc();
    	Double totalAmount;
    	//check existing transaction
    	if(transaction == null) {
    		totalAmount = newTransaction.getTransferAmount();
    	}else {
    		totalAmount = transaction.getTotalAmount() + newTransaction.getTransferAmount();
    	}
    	
    	newTransaction.setTotalAmount(totalAmount);
    	transactionRepo.save(newTransaction);
    	this.sendMessage(newTransaction);
    }
    
    private void sendMessage(Transaction transaction){
        rabbitTemplate.convertAndSend(exchange,routingkey, transaction);
    }
    
    
}
