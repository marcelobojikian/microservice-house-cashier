package com.cashhouse.cashier.repository;

import static com.cashhouse.cashier.util.EntityFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

@DataJpaTest
public class TransactionRepositoryTest {
	
	@Autowired
	private TestEntityManager em;

	@Autowired
	private CashierRepository cashierRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void mustReturnTransactionCreated() {
		
		Cashier cashier = cashierRepository.findById(3)
				.orElseThrow(() -> fail("Cashier 3 not found") );
		
		Transaction transaction = createTransaction(99.98, Status.FINISHED, Action.DEPOSIT);
		transaction.setCashier(cashier);

		Long idCreated = em.persist(transaction).getId();
		
		Transaction transactionCreated = transactionRepository.findById(idCreated)
				.orElseThrow(() -> fail("Transaction not persisted") );

		assertNotNull(transactionCreated);
		assertNotNull(transactionCreated.getCreatedDate());
		assertNull(transactionCreated.getUpdatedDate());
		assertEquals(cashier, transactionCreated.getCashier());
		assertEquals(Action.DEPOSIT, transactionCreated.getAction());
		assertEquals(Status.FINISHED, transactionCreated.getStatus());
		assertTrue(new BigDecimal("99.98").equals(transactionCreated.getValue()));
		
		transactionCreated.setValue(new BigDecimal("1.22"));
		
		em.persistAndFlush(transactionCreated);

		Transaction transactionUpdate = transactionRepository.findById(idCreated)
				.orElseThrow(() -> fail("Transaction not persisted") );

		assertNotNull(transactionUpdate.getUpdatedDate());

	}

	@Test
	public void mustReturnOneTransaction() {
		
		Cashier cashier = cashierRepository.findById(2)
				.orElseThrow(() -> fail("Cashier 2 not found") );

		List<Transaction> transactions = transactionRepository.findByCashier(cashier);
		
		assertTrue(transactions.size() == 1);
		
	}

	@Test
	public void mustReturnTransactionNull() {
		Optional<Transaction> transaction = transactionRepository.findById(-1);
		assertTrue(transaction.isEmpty());
	}

}
