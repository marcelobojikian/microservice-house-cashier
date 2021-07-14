package com.cashhouse.cashier.repository;

import static com.cashhouse.cashier.util.EntityFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		
		Optional<Cashier> optCashier = cashierRepository.findById(3);
		assertTrue(optCashier.isPresent());
		
		Cashier cashier = optCashier.get();
		
		Transaction transaction = createTransaction(new BigDecimal("99.98"), Status.FINISHED, Action.DEPOSIT);
		transaction.setCashier(cashier);

		Long idCreated = em.persist(transaction).getId();
		
		Optional<Transaction> optTransaction = transactionRepository.findById(idCreated);
		assertTrue(optTransaction.isPresent());
		
		Transaction transactionCreated = optTransaction.get();

		assertNotNull(transactionCreated);
		assertNotNull(transactionCreated.getCreatedDate());
		assertNull(transactionCreated.getUpdatedDate());
		assertEquals(cashier, transactionCreated.getCashier());
		assertEquals(Action.DEPOSIT, transactionCreated.getAction());
		assertEquals(Status.FINISHED, transactionCreated.getStatus());
		assertTrue(new BigDecimal("99.98").equals(transactionCreated.getValue()));
		
		transactionCreated.setValue(new BigDecimal("1.22"));
		
		em.persistAndFlush(transactionCreated);

		Optional<Transaction> optTransactionUpdate = transactionRepository.findById(idCreated);
		assertTrue(optTransactionUpdate.isPresent());
		Transaction transactionUpdate = optTransactionUpdate.get();

		assertNotNull(transactionUpdate.getUpdatedDate());

	}

	@Test
	public void mustReturnOneTransaction() {

		Optional<Cashier> optCashier = cashierRepository.findById(2);
		assertTrue(optCashier.isPresent());
		
		Cashier cashier = optCashier.get();

		List<Transaction> transactions = transactionRepository.findByCashier(cashier);
		
		assertTrue(transactions.size() == 1);
		
	}

	@Test
	public void mustReturnTransactionNull() {
		Optional<Transaction> transaction = transactionRepository.findById(-1);
		assertTrue(transaction.isEmpty());
	}

}
