package com.cashhouse.cashier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.service.TransactionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/transactions/{id}")
public class TransactionActionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/finish")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "Returns finished transaction entity", response = Transaction.class)
	public Transaction finish(@PathVariable Long id) {
		return transactionService.finish(id);
	}

}
