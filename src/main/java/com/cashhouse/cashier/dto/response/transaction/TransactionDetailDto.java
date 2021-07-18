package com.cashhouse.cashier.dto.response.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.cashhouse.cashier.controller.CashierController;
import com.cashhouse.cashier.controller.TransactionController;
import com.cashhouse.cashier.dto.response.cashier.CashierDetailDto;
import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;

import lombok.Getter;

@Getter
public class TransactionDetailDto extends RepresentationModel<TransactionDetailDto>
		implements Comparable<TransactionDetailDto> {

	Long id;

	String cashier;

	CashierDetailDto cashierDto;

	Status status;

	Action action;

	BigDecimal value;

	LocalDateTime createdDate;

	LocalDateTime updatedDate;

	public TransactionDetailDto(Transaction transaction) {

		Cashier cashier = transaction.getCashier();

		this.id = transaction.getId();
		this.cashier = cashier.getName();
		this.status = transaction.getStatus();
		this.action = transaction.getAction();
		this.value = transaction.getValue();
		this.createdDate = transaction.getCreatedDate();
		this.updatedDate = transaction.getUpdatedDate();

		Link selfLink = linkTo(methodOn(TransactionController.class).findById(id)).withSelfRel();
		this.add(selfLink);

		Link cashierLink = linkTo(methodOn(CashierController.class).findById(cashier.getId())).withRel("cashier");
		this.add(cashierLink);

	}

	@Override
	public int compareTo(TransactionDetailDto dto) {
		return createdDate.compareTo(dto.getCreatedDate());
	}

}
