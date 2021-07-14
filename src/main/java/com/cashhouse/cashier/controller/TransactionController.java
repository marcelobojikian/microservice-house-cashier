package com.cashhouse.cashier.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Locale;

import javax.validation.Valid;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cashhouse.cashier.dto.factory.PageableDto;
import com.cashhouse.cashier.dto.factory.TransactionDtoFactory;
import com.cashhouse.cashier.dto.request.transaction.CreateTransaction;
import com.cashhouse.cashier.dto.response.transaction.TransactionDetailDto;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.service.TransactionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("")
	@ApiOperation(value = "Return a list with all transaction", response = TransactionDetailDto[].class)
	public ResponseEntity<Page<?>> findAll(
			@RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) String language,
			@PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Direction.DESC) Pageable pageable) {

		Page<Transaction> transactions = transactionService.findAll(pageable);

		if (transactions.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		Locale locale = language == null ? LocaleContextHolder.getLocale() : Locale.forLanguageTag(language);
		
		TransactionDtoFactory factory = new TransactionDtoFactory(pageable, locale);
		PageableDto dto = factory.asPage(transactions);
		
		HttpStatus httpStatus = dto.isPartialPage() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
		
		return new ResponseEntity<>(dto.asPage(pageable), httpStatus);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return transaction entity by id", response = TransactionDetailDto.class)
	public TransactionDetailDto findById(@PathVariable Long id) {
		Transaction transaction = transactionService.findById(id);
		return new TransactionDetailDto(transaction);
	}

	@PostMapping("/deposit")
	@ApiOperation(value = "Returns transaction deposit created entity", response = TransactionDetailDto.class)
	public ResponseEntity<TransactionDetailDto> createDepoist(@RequestBody @Valid CreateTransaction content,
			UriComponentsBuilder uriBuilder) {

		Long cashierId = content.getCashier();
		BigDecimal value = content.getValue();

		Transaction entity = transactionService.createDeposit(cashierId, value);

		URI uri = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).body(new TransactionDetailDto(entity));

	}

	@PostMapping("/withdraw")
	@ApiOperation(value = "Returns transaction withdraw created entity", response = TransactionDetailDto.class)
	public ResponseEntity<TransactionDetailDto> createWithdraw(@RequestBody @Valid CreateTransaction content,
			UriComponentsBuilder uriBuilder) {

		Long cashierId = content.getCashier();
		BigDecimal value = content.getValue();

		Transaction entity = transactionService.createWithdraw(cashierId, value);

		URI uri = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(entity.getId()).toUri();

		return ResponseEntity.created(uri).body(new TransactionDetailDto(entity));

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted", response = TransactionDetailDto.class)
	public void detele(@PathVariable Long id) {
		transactionService.delete(id);
	}

}
