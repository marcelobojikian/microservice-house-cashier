package com.cashhouse.cashier.dto.response.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

import lombok.Getter;

@Getter
public class TransactionDetailDto implements Comparable<TransactionDetailDto> {

	Long id;

	String cashier;

	Status status;

	Action action;

	BigDecimal value;

	LocalDateTime createdDate;

	LocalDateTime updatedDate;
	
	public TransactionDetailDto(Transaction transaction) {
		this.id = transaction.getId();
		this.cashier = transaction.getCashier().getName();
		this.status = transaction.getStatus();
		this.action = transaction.getAction();
		this.value = transaction.getValue();
		this.createdDate = transaction.getCreatedDate();
		this.updatedDate = transaction.getUpdatedDate();
	}

	@Override
	public int compareTo(TransactionDetailDto dto) {
		return createdDate.compareTo(dto.getCreatedDate());
	}

}
