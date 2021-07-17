package com.cashhouse.cashier.service;

import static com.cashhouse.cashier.util.EntityFactory.createCashier;
import static com.cashhouse.cashier.util.EntityFactory.createTransaction;
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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest
class TransactionServiceTest {
	
	@Autowired
	private TransactionService transactionService;

	@MockBean
	private CashierRepository cashierRepository;

	@MockBean
	private TransactionRepository transactionRepository;

	@Test
	public void whenFindById_thenThrowEntityNotFoundException() throws Exception {

		when(transactionRepository.findById(1l)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> {
			transactionService.findById(1L);
		});

	}

	@Test
	public void whenFindById_thenReturnEntityObject() throws Exception {
		
		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, energy);

		when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.of(transaction));

		Transaction transactionExpect = transactionService.findById(1L);

		assertEquals(transactionExpect, transaction);

	}

	@Test
	public void whenFindAll_thenReturnObjectArray() throws Exception {

		PageRequest pagination = PageRequest.of(1, 10);

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		
		Transaction transactionOne = createTransaction(1l, new BigDecimal("23.08"), Status.SENDED, Action.DEPOSIT, energy);
		Transaction transactionTwo = createTransaction(2l, new BigDecimal("1.84"), Status.FINISHED, Action.WITHDRAW, energy);

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

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, energy);

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

		Cashier energy = createCashier(1l, "Energy", new BigDecimal("12.3"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.WITHDRAW, energy);

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

		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.WITHDRAW);

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
