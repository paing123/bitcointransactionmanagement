package com.anymindgroup.bitcoinmanagement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.anymindgroup.bitcoinmanagement.dao.TransactionRepository;
import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.model.Transaction;

@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {
	
    private final TransactionRepository transactionRepo;
    
    @Autowired
    public TransactionQueryServiceImpl(TransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }
        
    @Override
    @Async
    public CompletableFuture<List<TransactionDto>> findByDatetimeBetween(GregorianCalendar startDatetime, GregorianCalendar endDatetime){
    	
    	List<Transaction> transList = transactionRepo.findByDatetimeBetween(startDatetime, endDatetime);
    	List<TransactionDto> transDtoList = new ArrayList<>();
    	
    	List<GregorianCalendar> endOfHourIntervals = this.getEndOfHourIntervals(startDatetime, endDatetime);
    	
    	if(!transList.isEmpty()) {
    		//get time for every 1 hour
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
    
}
