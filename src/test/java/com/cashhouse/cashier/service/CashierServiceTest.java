package com.cashhouse.cashier.service;

import static com.cashhouse.cashier.model.EntityFactory.createCashier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.repository.CashierRepository;
import com.cashhouse.cashier.repository.TransactionRepository;

@SpringBootTest
class CashierServiceTest {

	@Autowired
	private CashierService cashierService;

	@MockBean
	private CashierRepository cashierRepository;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.findById(1L);
		});

	}

	@Test
	public void whenFindById_thenReturnEntityObject() throws Exception {

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));

		Cashier cashierExpect = cashierService.findById(1L);

		assertEquals(cashierExpect, energy);

	}

	@Test
	public void whenFindAll_thenReturnObjectArray() throws Exception {

		PageRequest pagination = PageRequest.of(1, 10);

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		Cashier garbage = createCashier(2l, "Garbage", new BigDecimal("4.2"), new BigDecimal("56.6"));

		List<Cashier> cashiers = Arrays.asList(energy, garbage);
		Page<Cashier> cashiersPage = new PageImpl<>(cashiers, pagination, cashiers.size());

		when(cashierRepository.findAll(pagination)).thenReturn(cashiersPage);

		Page<Cashier> results = cashierService.findAll(pagination);

		assertEquals(results, cashiersPage);

	}

	@Test
	public void whenCreate_thenReturnEntityObject() throws Exception {

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));

		when(cashierRepository.save(energy)).thenReturn(energy);

		Cashier cashier = cashierService.create(energy);

		assertEquals(cashier.getId(), 1l);
		assertEquals(cashier.getName(), "Energy");
		assertEquals(cashier.getStarted(), BigDecimal.valueOf(12.3));
		assertEquals(cashier.getBalance(), BigDecimal.valueOf(12.3));

	}

	@Test
	public void whenUpdate_thenReturnEntityObject() throws Exception {

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		Cashier energyNew = createCashier(1l, "Energy UP", new BigDecimal("3.1"), new BigDecimal("3.2"));

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
		when(cashierRepository.save(energy)).thenReturn(energy);

		Cashier cashier = cashierService.update(1l, energyNew);

		assertEquals(cashier.getId(), 1l);
		assertEquals(cashier.getName(), "Energy UP");
		assertEquals(cashier.getStarted(), BigDecimal.valueOf(3.1));
		assertEquals(cashier.getBalance(), BigDecimal.valueOf(3.2));

	}

	@Test
	public void whenUpdate_thenThrowEntityNotFoundException() throws Exception {

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		when(cashierRepository.findById(99l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.update(99l, energy);
		});

	}

	@Test
	public void whenDelete_thenReturnVoid() throws Exception {

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
		doNothing().when(cashierRepository).delete(energy);

		cashierService.delete(1l);

	}

	@Test
	public void whenDelete_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.delete(1l);
		});

	}

}
