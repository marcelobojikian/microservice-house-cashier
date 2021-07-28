package com.cashhouse.cashier.model;

import static com.cashhouse.cashier.util.BuilderFactory.createCashier;
import static com.cashhouse.cashier.util.BuilderFactory.createTransaction;
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
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.DEPOSIT)
									.value("3.33").result();
		
		transaction.setCashier(cashier);
		
		assertEquals(Status.SENDED, transaction.getStatus());
		assertEquals(Action.DEPOSIT, transaction.getAction());
		assertEquals(new BigDecimal("3.33"), transaction.getValue());
		assertEquals(cashier, transaction.getCashier());
		
	}

	@Test
	void whenCommitDeposit_thenReturnValidEntityObject() {
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.DEPOSIT)
									.value("3.33").result();

		transaction.setCashier(cashier);
		transaction.commit();
		
		assertEquals(new BigDecimal("13.33"), cashier.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithdraw_thenReturnValidEntityObject() {
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();

		transaction.setCashier(cashier);
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), cashier.getBalance());
		assertTrue(transaction.isFinished());
		
	}

	@Test
	void whenCommitWithStatusFinished_thenThrowIllegalStateException() {
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();
		
		transaction.setCashier(cashier);
		transaction.commit();
		
		assertEquals(new BigDecimal("6.67"), cashier.getBalance());
		assertTrue(transaction.isFinished());

		assertThrows(IllegalStateException.class, () -> {
			transaction.commit();
		});
		
		
	}

}
