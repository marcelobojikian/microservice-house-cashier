package com.cashhouse.cashier.dto.response.transaction;

import org.springframework.data.domain.Page;

import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionListDto extends SimpleListDto<TransactionDetailDto, Transaction> {
	
	public TransactionListDto(Page<Transaction> transactions) {
		super(transactions);
	}

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
