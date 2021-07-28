package com.cashhouse.cashier.util;

import com.cashhouse.cashier.model.CashierBuilder;
import com.cashhouse.cashier.model.TransactionBuilder;

public class BuilderFactory {
	
	private BuilderFactory() {
		
	}
	
	public static CashierBuilder createCashier() {
		return CashierBuilder.createBuilder();
	}
	
	public static TransactionBuilder createTransaction() {
		return TransactionBuilder.createBuilder();
	}

}
