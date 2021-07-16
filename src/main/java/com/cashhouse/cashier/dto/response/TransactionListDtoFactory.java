package com.cashhouse.cashier.dto.response;

import com.cashhouse.cashier.dto.factory.ResponseListDtoFactory;
import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionDetailDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionListDtoFactory extends ResponseListDtoFactory<TransactionDetailDto, Transaction> {

	public TransactionListDtoFactory(SimpleListDto<TransactionDetailDto, Transaction> defaultList) {
		super(defaultList);
	}

}
