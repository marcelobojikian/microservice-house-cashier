package com.cashhouse.cashier.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

class TransactionTest {


	@Test
	void whenCreateTransaction_thenReturnValidEntityObject() {
		
		Cashier cashier = new Cashier("Main cashier", new BigDecimal("2.00"), new BigDecimal("10.00"));
		Transaction transaction = new Transaction();
		
		transaction.setAction(Action.DEPOSIT);
		transaction.setValue(new BigDecimal("3.33"));
		transaction.setCashier(cashier);
		
		assertEquals(Status.SENDED, transaction.getStatus());
		assertEquals(Action.DEPOSIT, transaction.getAction());
		assertEquals(new BigDecimal("3.33"), transaction.getValue());
		assertEquals(cashier, transaction.getCashier());
		
	}

	@Test
	void whenCommitDeposit_thenReturnValidEntityObject() {

		Cashier cashier = new Cashier("Main cashier", new BigDecimal("2.00"), new BigDecimal("10.00"));
		Transaction transaction = new Transaction();
		
		transaction.setAction(Action.DEPOSIT);
		transaction.setValue(new BigDecimal("3.33"));
		transaction.setCashier(cashier);
		
		transaction.commit();
		
		assertEquals(new BigDecimal("13.33"), cashier.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithdraw_thenReturnValidEntityObject() {

		Cashier cashier = new Cashier("Main cashier", new BigDecimal("2.00"), new BigDecimal("10.00"));
		Transaction transaction = new Transaction();
		
		transaction.setAction(Action.WITHDRAW);
		transaction.setValue(new BigDecimal("3.33"));
		transaction.setCashier(cashier);
		
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), cashier.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithStatusFinished_thenThrowIllegalStateException() {

		Cashier cashier = new Cashier("Main cashier", new BigDecimal("2.00"), new BigDecimal("10.00"));
		Transaction transaction = new Transaction();
		
		transaction.setAction(Action.WITHDRAW);
		transaction.setValue(new BigDecimal("3.33"));
		transaction.setCashier(cashier);
		
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), cashier.getBalance());
		assertTrue(transaction.isFinished());
		

		assertThrows(IllegalStateException.class, () -> {
			transaction.commit();
		});
		
		
	}

}
