package com.cashhouse.cashier.controller;

import static com.cashhouse.cashier.util.EntityFactory.createCashier;
import static com.cashhouse.cashier.util.EntityFactory.createTransaction;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cashhouse.cashier.config.validation.CustomRestValidationHandler;
import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;
import com.cashhouse.cashier.service.TransactionService;
import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionActionControllerTest extends SampleRequest {

	private static final EntityNotFoundException TRANSACTION_NOT_FOUND = new EntityNotFoundException("transaction.not.found");

	@MockBean
	private CustomRestValidationHandler restValidationHandler;

	@MockBean
	private TransactionService transactionService;
	
	@Test
	void whenFinishTransaction_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Cashier entity = createCashier(3l, "Post Test Cashier", new BigDecimal("11.23"), new BigDecimal("123.23"));
		Transaction transaction = createTransaction(1l, new BigDecimal("9.99"), Status.SENDED, Action.WITHDRAW, entity);
		
		when(transactionService.finish(any(Long.class))).thenReturn(transaction);

		// @formatter:off
		body();
		post("/transactions/1/finish")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/transactions/1"))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.cashier", is("Post Test Cashier")))
			.andExpect(jsonPath("$.status", is(Status.SENDED.name())))
			.andExpect(jsonPath("$.action", is(Action.WITHDRAW.name())))
			.andExpect(jsonPath("$.value", is(9.99)));
        // @formatter:on
		
	}

	@Test
	void whenFinishTransactionWithInvalidId_thenReturnResponseStatusNotFound() throws Exception {
		
		doThrow(TRANSACTION_NOT_FOUND).when(transactionService).finish(any(Long.class));

		body();
		post("/transactions/999/finish").andExpect(status().isNotFound());
		
	}

}
