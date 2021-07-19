package com.cashhouse.cashier.controller;

import static com.cashhouse.cashier.model.EntityFactory.createCashier;
import static com.cashhouse.cashier.model.EntityFactory.createTransaction;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cashhouse.cashier.config.validation.CustomRestValidationHandler;
import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;
import com.cashhouse.cashier.service.TransactionService;
import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest extends SampleRequest {

	private static final EntityNotFoundException TRANSACTION_NOT_FOUND = new EntityNotFoundException(
			"transaction.not.found");

	@MockBean
	private CustomRestValidationHandler restValidationHandler;

	@MockBean
	private TransactionService transactionService;

	/**
	 * methods findById
	 */

	@Test
	void whenFindById_thenReturnEntityObject() throws Exception {

		Cashier entity = createCashier(3l, "Rent & Clean", new BigDecimal("12.45"), new BigDecimal("3.11"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, entity);
		when(transactionService.findById(1l)).thenReturn(transaction);

		// @formatter:off
		get("/transactions/1")
	        .andExpect(content().contentType(MediaTypes.HAL_JSON))
	        .andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.cashier", is("Rent & Clean")))
			.andExpect(jsonPath("$.status", is(Status.SENDED.name())))
			.andExpect(jsonPath("$.action", is(Action.DEPOSIT.name())))
			.andExpect(jsonPath("$.value", is(9.99)));
        // @formatter:on

	}

	@Test
	void whenFindById_thenReturnResponseStatusNotFound() throws Exception {

		when(transactionService.findById(any(Long.class))).thenThrow(TRANSACTION_NOT_FOUND);

		get("/transactions/999").andExpect(status().isNotFound());

	}

	/**
	 * methods findAll
	 */

	@Test
	void whenFindAll_thenReturnNoContent() throws Exception {

		Page<Transaction> page = new PageImpl<>(Collections.emptyList());

		when(transactionService.findAll(any(), any())).thenReturn(page);

		get("/transactions").andExpect(status().isNoContent());

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusOk() throws Exception {

		Cashier energy = createCashier(3l, "Energy", new BigDecimal("12.45"), new BigDecimal("3.11"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, energy, LocalDateTime.now().minusDays(2));

		Page<Transaction> page = new PageImpl<>(Arrays.asList(transaction));

		when(transactionService.findAll(any(), any())).thenReturn(page);

		// @formatter:off
		get("/transactions")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk());
        // @formatter:on

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusPartialContent() throws Exception {

		Cashier energy = createCashier(3l, "Energy", new BigDecimal("12.45"), new BigDecimal("3.11"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, energy, LocalDateTime.now().minusDays(2));
		
		Page<Transaction> page = new PageImpl<>(Arrays.asList(transaction), PageRequest.of(1, 8), 20);

		when(transactionService.findAll(any(), any())).thenReturn(page);

		// @formatter:off		
		get("/transactions?size=2&sort=cashier")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isPartialContent());
        // @formatter:on

	}

	@Test
	void whenFindAll_thenReturnListObjectsDifferentLanguage() throws Exception {

		Cashier energy = createCashier(3l, "Energy", new BigDecimal("12.45"), new BigDecimal("3.11"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, energy, LocalDateTime.now().minusDays(2));

		Page<Transaction> page = new PageImpl<>(Arrays.asList(transaction));

		when(transactionService.findAll(any(), any())).thenReturn(page);

		// @formatter:off
		
		invokeGet(
			customGet("/transactions")
				.header(HttpHeaders.ACCEPT_LANGUAGE, "pt-BR")
				.contentType(MediaType.APPLICATION_JSON)
			)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
        // @formatter:on

	}

	/**
	 * methods create deposit
	 */

	void whenCreateDepositWithoutBody_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateDepositWithNullCashier_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateDepositWithNullValue_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateDepositWithInvalidCashier_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateDepositWithInvalidValue_thenReturnResponseStatusBadRequest() throws Exception {
	}

	@Test
	void whenCreateDeposit_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Cashier entity = createCashier(3l, "Post Test Cashier", new BigDecimal("11.23"), new BigDecimal("123.23"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.DEPOSIT, entity);

		when(transactionService.createDeposit(any(Long.class), any(BigDecimal.class))).thenReturn(transaction);

		// @formatter:off
		body()
			.add("cashier", "3")
			.add("value", new BigDecimal("9.99"));

		post("/transactions/deposit")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/transactions/1"))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.cashier", is("Post Test Cashier")))
			.andExpect(jsonPath("$.status", is(Status.SENDED.name())))
			.andExpect(jsonPath("$.action", is(Action.DEPOSIT.name())))
			.andExpect(jsonPath("$.value", is(9.99)));
        // @formatter:on

	}

	/**
	 * methods update
	 */

	void whenCreateWithdrawWithoutBody_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateWithdrawWithNullCashier_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateWithdrawWithNullValue_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateWithdrawWithInvalidCashier_thenReturnResponseStatusBadRequest() throws Exception {
	}

	void whenCreateWithdrawWithInvalidValue_thenReturnResponseStatusBadRequest() throws Exception {
	}

	@Test
	void whenCreateWithdraw_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Cashier entity = createCashier(3l, "Post Test Cashier", new BigDecimal("11.23"), new BigDecimal("123.23"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.WITHDRAW, entity);

		when(transactionService.createWithdraw(any(Long.class), any(BigDecimal.class))).thenReturn(transaction);

		// @formatter:off
		body()
			.add("cashier", "3")
			.add("value", new BigDecimal("9.99"));

		post("/transactions/withdraw")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/transactions/1"))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.cashier", is("Post Test Cashier")))
			.andExpect(jsonPath("$.status", is(Status.SENDED.name())))
			.andExpect(jsonPath("$.action", is(Action.WITHDRAW.name())))
			.andExpect(jsonPath("$.value", is(9.99)));
        // @formatter:on

	}

	/**
	 * methods delete
	 */

	@Test
	void whenDeleteWithInvalidId_thenReturnResponseStatusNotFound() throws Exception {

		doThrow(TRANSACTION_NOT_FOUND).when(transactionService).delete(any(Long.class));

		delete("/transactions/999").andExpect(status().isNotFound());

	}

	@Test
	void whenDelete_thenReturnEntityObject() throws Exception {

		doNothing().when(transactionService).delete(any(Long.class));

		delete("/transactions/1").andExpect(status().isOk());

	}

}
