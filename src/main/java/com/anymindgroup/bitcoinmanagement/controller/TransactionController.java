package com.anymindgroup.bitcoinmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anymindgroup.bitcoinmanagement.dto.TransactionDto;
import com.anymindgroup.bitcoinmanagement.service.TransactionService;

@RestController
@RequestMapping("/restapi")
public class TransactionController {
	
    TransactionService transactionService;
    
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
//    @GetMapping(value="/")
//    public String getHome() {
//    	return "This is testing";
//    }
    
    @GetMapping(value="/transactions")
    public List<TransactionDto> findByDatetimeBetween(@RequestBody TransactionDto transaction) throws Exception{
        return transactionService.findByDatetimeBetween(transaction.getStartDatetime()
        												, transaction.getEndDatetime());
    }           
//    
//    @GetMapping(value="/students/{id}")
//    public Student getStudentById(@PathVariable("id") @Min(1) int id) {
//        Student std = studentservice.findById(id)
//                                    .orElseThrow(()->new StudentNotFoundException("Student id "+id+" is Not Found!"));
//        return std;
//    }           
    
    @PostMapping(value="/transactions")
    public TransactionDto addTransaction(@Valid @RequestBody TransactionDto transaction) throws Exception{
        return transactionService.save(transaction);
    }           
    
//    @PutMapping(value="/students/{id}")
//    public Student updateStudent(@PathVariable("id") @Min(1) int id, @Valid @RequestBody Student newstd) {
//        Student stdu = studentservice.findById(id)
//                                     .orElseThrow(()->new StudentNotFoundException("Student id "+id+" is Not Found!"));
//        stdu.setName(newstd.getName());
//        stdu.setAge(newstd.getAge());
//        stdu.setEmail(newstd.getEmail());
//        return studentservice.save(stdu);   
//    }           
    
//    @DeleteMapping(value="/students/{id}")
//    public String deleteStudent(@PathVariable("id") @Min(1) int id) {
//        Student std = studentservice.findById(id)
//                                     .orElseThrow(()->new StudentNotFoundException("Student id "+id+" is Not Found!"));
//        studentservice.deleteById(std.getId());
//        return "Student with ID :"+id+" is deleted";            
//    }
    
    private void convertDateTime(TransactionDto transaction) throws Exception{
//    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
//    	Date start = formatter.parse(transaction.getStartDatetime());
//    	String strStart = formatter.format(start);
//    	
//    	System.out.println(strStart);
    	
    }
}
