package com.cashhouse.cashier.dto.response.transaction;

import org.springframework.data.domain.Page;

import com.cashhouse.cashier.dto.factory.type.GroupListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionCashierHeaderDto extends GroupListDto<TransactionDetailDto, Transaction> {

	public TransactionCashierHeaderDto(Page<Transaction> transactions) {
		super(transactions);
	}

	@Override
	public String getHeader(Transaction transaction) {
		return transaction.getCashier().getName();
	}

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
