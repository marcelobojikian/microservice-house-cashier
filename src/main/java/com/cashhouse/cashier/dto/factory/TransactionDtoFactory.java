package com.cashhouse.cashier.dto.factory;

import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cashhouse.cashier.dto.response.transaction.TransactionCashierHeaderDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionDateHeaderDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionDtoFactory {
	
	public static final String DATE_ORDER = "createdDate";
	public static final String CASHIER_ORDER = "cashier";

	private Sort sort;
	
	private Locale locale;
	
	public TransactionDtoFactory(Pageable pageable, Locale locale){
		this(pageable.getSort(), locale);
	}
	
	public TransactionDtoFactory(Sort sort, Locale locale){
		this.locale = locale;
		this.sort = sort;
	}
	
	public PageableDto asPage(Page<Transaction> transactions) {
		
		if(sort.getOrderFor(DATE_ORDER) != null) {
			return new TransactionDateHeaderDto(transactions, FormatStyle.LONG, locale);
		} else if(sort.getOrderFor(CASHIER_ORDER) != null) {
			return new TransactionCashierHeaderDto(transactions);
		} else {
			return new TransactionListDto(transactions);
		}
		
	}

}
