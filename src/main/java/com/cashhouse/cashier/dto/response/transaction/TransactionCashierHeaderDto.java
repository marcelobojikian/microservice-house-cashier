package com.cashhouse.cashier.dto.response.transaction;

import com.cashhouse.cashier.dto.factory.type.GroupListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionCashierHeaderDto extends GroupListDto<TransactionDetailDto, Transaction> {

	@Override
	public String getHeader(Transaction transaction) {
		return transaction.getCashier().getName();
	}

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
