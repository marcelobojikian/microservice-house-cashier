package com.cashhouse.cashier.service;

import static com.cashhouse.cashier.util.BuilderFactory.createCashier;
import static com.cashhouse.cashier.util.BuilderFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;
import com.cashhouse.cashier.repository.CashierRepository;
import com.cashhouse.cashier.repository.TransactionRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

class TransactionServiceTest {
	
	private TransactionService transactionService;

	@Mock
	private CashierRepository cashierRepository;

	@Mock
	private TransactionRepository transactionRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		transactionService = new TransactionServiceImpl(cashierRepository, transactionRepository);
	}

	@Test
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(transactionRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.findById(1L);
		});

	}

	@Test
	public void whenFindById_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Transaction transaction = createTransaction()
				.id(1l)
				.action(Action.DEPOSIT)
				.value("9.99")
				.cashier(energy).result();

		when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.of(transaction));

		Transaction transactionExpect = transactionService.findById(1L);

		assertEquals(transactionExpect, transaction);

	}

	@Test
	public void whenFindAll_thenReturnObjectArray() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Transaction transactionOne = createTransaction()
				.id(1l)
				.action(Action.DEPOSIT)
				.value("23.08")
				.cashier(energy).result();
		
		Transaction transactionTwo = createTransaction()
				.id(2l)
				.action(Action.WITHDRAW)
				.value("1.84")
				.cashier(energy).result();

		PageRequest pagination = PageRequest.of(1, 10);

		List<Transaction> transactions = Arrays.asList(transactionOne, transactionTwo);
		Page<Transaction> transactionsPage = new PageImpl<>(transactions, pagination, transactions.size());
		
		Predicate predicate = new BooleanBuilder();

		when(transactionRepository.findAll(predicate, pagination)).thenReturn(transactionsPage);

		Page<Transaction> results = transactionService.findAll(predicate, pagination);

		assertEquals(results, transactionsPage);

	}

	@Test
	public void whenCreateDeposit_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.createDeposit(1l, new BigDecimal("9.99"));
		});

	}

	@Test
	public void whenCreateDeposit_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Transaction transaction = createTransaction()
				.id(1l)
				.action(Action.DEPOSIT)
				.value("9.99")
				.cashier(energy).result();

		when(cashierRepository.findById(any(Long.class))).thenReturn(Optional.of(energy));
		when(transactionRepository.save(any())).thenReturn(transaction);

		Transaction transactionExprected = transactionService.createDeposit(1l, new BigDecimal("9.99"));

		assertEquals(transactionExprected.getId(), 1l);
		assertEquals(transactionExprected.getStatus(), Status.SENDED);
		assertEquals(transactionExprected.getAction(), Action.DEPOSIT);
		assertEquals(transactionExprected.getCashier(), energy);
		assertEquals(transactionExprected.getValue(), BigDecimal.valueOf(9.99));

	}

	@Test
	public void whenCreateWithdraw_thenThrowEntityNotFoundException() throws Exception {

		when(cashierRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.createWithdraw(1l, new BigDecimal("9.99"));
		});

	}

	@Test
	public void whenCreateWithdraw_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier()
				.id(1l)
				.name("Energy")
				.started("12.3").result();
		
		Transaction transaction = createTransaction()
				.id(1l)
				.action(Action.WITHDRAW)
				.value("9.99")
				.cashier(energy).result();

		when(cashierRepository.findById(any(Long.class))).thenReturn(Optional.of(energy));
		when(transactionRepository.save(any())).thenReturn(transaction);

		Transaction transactionExprected = transactionService.createWithdraw(1l, new BigDecimal("9.99"));

		assertEquals(transactionExprected.getId(), 1l);
		assertEquals(transactionExprected.getStatus(), Status.SENDED);
		assertEquals(transactionExprected.getAction(), Action.WITHDRAW);
		assertEquals(transactionExprected.getCashier(), energy);
		assertEquals(transactionExprected.getValue(), BigDecimal.valueOf(9.99));

	}

	@Test
	public void whenDelete_thenReturnVoid() throws Exception {
		
		Transaction transaction = createTransaction()
				.id(1l)
				.action(Action.WITHDRAW)
				.value("9.99").result();

		when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.of(transaction));
		doNothing().when(transactionRepository).delete(transaction);

		transactionService.delete(1l);

	}

	@Test
	public void whenDelete_thenThrowEntityNotFoundException() throws Exception {

		when(transactionRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.delete(1l);
		});

	}

	@Test
	public void whenFinish_thenReturnEntityObject() throws Exception {
		
		Transaction transaction = mock(Transaction.class);

		when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.of(transaction));
		doNothing().when(transaction).commit();
		when(transactionRepository.save(transaction)).thenReturn(transaction);

		transactionService.finish(1l);

	}

	@Test
	public void whenFinish_thenThrowEntityNotFoundException() throws Exception {

		when(transactionRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.finish(1l);
		});

	}

}
