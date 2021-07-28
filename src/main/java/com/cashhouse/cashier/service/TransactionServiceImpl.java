package com.cashhouse.cashier.service;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.repository.CashierRepository;
import com.cashhouse.cashier.repository.TransactionRepository;
import com.querydsl.core.types.Predicate;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final String CASHIER_NOT_FOUND = "cashier.not.found";
	private static final String TRANSACTION_NOT_FOUND = "transaction.not.found";

	private CashierRepository cashierRepository;

	private TransactionRepository transactionRepository;

	@Autowired	
	public TransactionServiceImpl(CashierRepository cashierRepository, TransactionRepository transactionRepository) {
		this.cashierRepository = cashierRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public Transaction findById(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));
	}

	@Override
	public Page<Transaction> findAll(Predicate predicate, Pageable pageable) {
		return transactionRepository.findAll(predicate, pageable);
	}

	@Override
	@Transactional
	public Transaction createDeposit(Long cashierId, BigDecimal value) {
		return create(cashierId, value, Action.DEPOSIT);
	}

	@Override
	@Transactional
	public Transaction createWithdraw(Long cashierId, BigDecimal value) {
		return create(cashierId, value, Action.WITHDRAW);
	}

	private Transaction create(Long cashierId, BigDecimal value, Action action) {

		Cashier cashier = cashierRepository.findById(cashierId)
				.orElseThrow(() -> new EntityNotFoundException(CASHIER_NOT_FOUND));

		Transaction transaction = new Transaction();

		transaction.setAction(action);
		transaction.setCashier(cashier);
		transaction.setValue(value);

		transaction = transactionRepository.save(transaction);

		return transaction;

	}

	@Override
	public void delete(Long id) {

		Transaction entity = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));

		transactionRepository.delete(entity);

	}

	@Override
	@Transactional
	public synchronized Transaction finish(Long id) {

		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND));

		transaction.commit();

		return transactionRepository.save(transaction);

	}

}
