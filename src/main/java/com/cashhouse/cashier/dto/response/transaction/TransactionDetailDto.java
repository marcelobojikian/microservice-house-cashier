package com.cashhouse.cashier.dto.response.transaction;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.cashhouse.cashier.controller.CashierController;
import com.cashhouse.cashier.controller.TransactionController;
import com.cashhouse.cashier.dto.response.cashier.CashierDetailDto;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionDetailDto extends RepresentationModel<TransactionDetailDto> {

	Long id;

	String cashier;

	CashierDetailDto cashierDto;

	Status status;

	Action action;

	BigDecimal value;

	LocalDateTime createdDate;

	LocalDateTime updatedDate;

	public TransactionDetailDto(Transaction transaction) {

		this.id = transaction.getId();
		this.cashier = transaction.getCashier().getName();
		this.status = transaction.getStatus();
		this.action = transaction.getAction();
		this.value = transaction.getValue();
		this.createdDate = transaction.getCreatedDate();
		this.updatedDate = transaction.getUpdatedDate();

		Link selfLink = linkTo(methodOn(TransactionController.class).findById(id)).withSelfRel();
		this.add(selfLink);

		Link cashierLink = linkTo(methodOn(CashierController.class).findById(transaction.getCashier().getId())).withRel("cashier");
		this.add(cashierLink);

	}

}
