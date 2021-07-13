package com.cashhouse.cashier.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cashhouse.cashier.model.Transaction;

public interface TransactionService {

	public Transaction findById(Long id);

	public Page<Transaction> findAll(Pageable pageable);

	public Transaction createDeposit(Long cashierId, BigDecimal value);

	public Transaction createwithdraw(Long cashierId, BigDecimal value);

	public void delete(Long id);

	/* Actions */

	public Transaction finish(Long id);
	

}
