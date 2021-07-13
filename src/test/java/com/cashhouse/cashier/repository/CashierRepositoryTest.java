package com.cashhouse.cashier.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cashhouse.cashier.model.Cashier;

@DataJpaTest
public class CashierRepositoryTest {

	@Autowired
	private CashierRepository repository;

	@Test
	public void mustReturnCashierNull() {
		Optional<Cashier> entity = repository.findById(-1);
		assertTrue(entity.isEmpty());
	}

	@Test
	public void mustReturnCashierCodeOne() {
		Optional<Cashier> entity = repository.findById(1);
		assertTrue(entity.isPresent());
		
		Cashier cashier = entity.get();
		
		assertEquals(cashier.getTransactions().size(), 2);
		
	}

	@Test
	public void mustReturnCashierCodeTwo() {
		Optional<Cashier> entity = repository.findById(2);
		assertTrue(entity.isPresent());
		
		Cashier cashier = entity.get();
		
		assertEquals(cashier.getTransactions().size(), 1);
	}

	@Test
	public void mustReturnCashierCodeThree() {
		Optional<Cashier> entity = repository.findById(3);
		assertTrue(entity.isPresent());
	}

}
