package com.cashhouse.cashier.service;

import static com.cashhouse.cashier.util.BuilderFactory.createCashier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.repository.CashierRepository;

class CashierServiceTest {

	private CashierService cashierService;

	@Mock
	private CashierRepository cashierRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		cashierService = new CashierServiceImpl(cashierRepository);
	}

	@Test
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.findById(1L);
		});

	}

	@Test
	public void whenFindById_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));

		Cashier cashierExpect = cashierService.findById(1L);
		
		verify(cashierRepository).findById(1l);
		assertEquals(cashierExpect, energy);

	}

	@Test
	public void whenFindAll_thenReturnObjectArray() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Cashier garbage = createCashier()
				.id(2l)
				.name("Garbage")
				.started("4.2")
				.balance("56.6").result();

		PageRequest pagination = PageRequest.of(1, 10);

		List<Cashier> cashiers = Arrays.asList(energy, garbage);
		Page<Cashier> cashiersPage = new PageImpl<>(cashiers, pagination, cashiers.size());

		when(cashierRepository.findAll(pagination)).thenReturn(cashiersPage);

		Page<Cashier> results = cashierService.findAll(pagination);

		assertEquals(results, cashiersPage);

	}

	@Test
	public void whenCreate_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();

		when(cashierRepository.save(energy)).thenReturn(energy);

		Cashier cashier = cashierService.create(energy);

		verify(cashierRepository).save(energy);
		assertEquals(cashier.getId(), 1l);
		assertEquals(cashier.getName(), "Energy");
		assertEquals(cashier.getStarted(), BigDecimal.valueOf(12.3));
		assertEquals(cashier.getBalance(), BigDecimal.valueOf(12.3));

	}

	@Test
	public void whenUpdate_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Cashier energyNew = createCashier()
				.id(1l)
				.name("Energy UP")
				.started("3.1")
				.balance("3.2").result();

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
		when(cashierRepository.save(energy)).thenReturn(energy);

		Cashier cashier = cashierService.update(1l, energyNew);

		verify(cashierRepository).save(energy);
		assertEquals(cashier.getId(), 1l);
		assertEquals(cashier.getName(), "Energy UP");
		assertEquals(cashier.getStarted(), BigDecimal.valueOf(3.1));
		assertEquals(cashier.getBalance(), BigDecimal.valueOf(3.2));

	}

	@Test
	public void whenUpdate_thenThrowEntityNotFoundException() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();

		when(cashierRepository.findById(99l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.update(99l, energy);
		});

	}

	@Test
	public void whenDelete_thenReturnVoid() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();

		when(cashierRepository.findById(1l)).thenReturn(Optional.of(energy));
		doNothing().when(cashierRepository).delete(energy);

		cashierService.delete(1l);
		
		verify(cashierRepository).delete(energy);

	}

	@Test
	public void whenDelete_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			cashierService.delete(1l);
		});

	}

}
