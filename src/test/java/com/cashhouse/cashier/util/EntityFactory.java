package com.cashhouse.cashier.util;

import java.math.BigDecimal;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

public class EntityFactory {

	private EntityFactory() {

	}

	/*
	 * Transaction Helper
	 */

	public static Transaction createTransaction(Long id, BigDecimal value, Status status, Action action, Cashier cashier) {

		Transaction transaction = createTransaction(id, value, status, action);
		transaction.setCashier(cashier);

		return transaction;
	}

	public static Transaction createTransaction(Long id, BigDecimal value, Status status, Action action) {

		Transaction transaction = createTransaction(value, status, action);
		transaction.setId(id);

		return transaction;
	}

	public static Transaction createTransaction(BigDecimal value, Status status, Action action) {

		Transaction transaction = new Transaction();
		transaction.setValue(value);
		transaction.setStatus(status);
		transaction.setAction(action);

		return transaction;
	}

	/*
	 * Cashier Helper
	 */

	public static Cashier createCashier(String name, BigDecimal started) {
		return createCashier(name, started, started);
	}

	public static Cashier createCashier(Long id, String name, BigDecimal started) {

		Cashier cashier = createCashier(name, started, started);
		cashier.setId(id);

		return cashier;
	}

	public static Cashier createCashier(Long id, String name, BigDecimal started, BigDecimal balance) {

		Cashier cashier = createCashier(name, started, balance);
		cashier.setId(id);

		return cashier;
	}

	public static Cashier createCashier(String name, BigDecimal started, BigDecimal balance) {

		Cashier cashier = new Cashier();
		cashier.setName(name);
		cashier.setStarted(started);
		cashier.setBalance(balance);

		return cashier;
	}

}
