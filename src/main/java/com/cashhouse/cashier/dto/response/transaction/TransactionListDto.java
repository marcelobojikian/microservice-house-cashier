package com.cashhouse.cashier.dto.response.transaction;

import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionListDto extends SimpleListDto<TransactionDetailDto, Transaction> {

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
