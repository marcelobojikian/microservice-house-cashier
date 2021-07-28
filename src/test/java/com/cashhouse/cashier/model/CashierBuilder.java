package com.cashhouse.cashier.model;

import java.math.BigDecimal;

public class CashierBuilder {
	
	private Cashier cashier;

	private CashierBuilder() {
		cashier = new Cashier();
	}
	
	public static CashierBuilder createBuilder() {
		return new CashierBuilder();
	}
	
	public CashierBuilder id(Long id) {
		cashier.setId(id);;
		return this;
	}
	
	public CashierBuilder name(String name) {
		cashier.setName(name);
		return this;
	}
	
	public CashierBuilder started(String started) {
		return started(new BigDecimal(started));
	}
	
	public CashierBuilder started(BigDecimal started) {
		cashier.setStarted(started);
		if(cashier.getBalance() == null) {
			cashier.setBalance(started);
		}
		return this;
	}
	
	public CashierBuilder balance(String balance) {
		return balance(new BigDecimal(balance));
	}
	
	public CashierBuilder balance(BigDecimal balance) {
		cashier.setBalance(balance);
		return this;
	}
	
	public Cashier result() {
		return cashier;
	}

}
