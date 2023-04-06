package com.anymindgroup.btctransactioncommandservice.dto;

import java.util.GregorianCalendar;

import lombok.Data;

@Data
public class TransactionDto {

	private Long transId;

	private GregorianCalendar startDatetime;

	private GregorianCalendar endDatetime;

	private GregorianCalendar datetime;

	private Double transferAmount;

	private Double totalAmount;

	private String status;
}
