package com.cashhouse.cashier.model;

import static com.cashhouse.cashier.util.BuilderFactory.createCashier;
import static com.cashhouse.cashier.util.BuilderFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.cashhouse.cashier.model.Transaction.Action;

class CashierTest {

	@Test
	void whenCreateCashier_thenReturnValidEntityObject() {
		
		Transaction transaction = createTransaction()
									.action(Action.WITHDRAW)
									.value("3.33").result();
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();
		
		cashier.setTransactions(Arrays.asList(transaction));

		assertEquals("Main cashier", cashier.getName());
		assertEquals(new BigDecimal("2.00"), cashier.getStarted());
		assertEquals(new BigDecimal("10.00"), cashier.getBalance());
		assertTrue(cashier.getTransactions().size() == 1);

	}

	@Test
	void whenDoDepoist_thenReturnValidEntityObject() {
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();

		cashier.doDeposit(new BigDecimal("5.50"));
		
		assertEquals(new BigDecimal("15.50"), cashier.getBalance());
		
	}

	@Test
	void whenDoWithdraw_thenReturnValidEntityObject() {
		
		Cashier cashier = createCashier()
				.name("Main cashier")
				.started("2.00")
				.balance("10.00").result();

		cashier.doWithdraw(new BigDecimal("5.50"));
		
		assertEquals(new BigDecimal("4.50"), cashier.getBalance());
		
	}

}
