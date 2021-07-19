package com.cashhouse.cashier.dto.response.cashier;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import com.cashhouse.cashier.controller.CashierController;
import com.cashhouse.cashier.model.Cashier;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CashierDetailDto extends RepresentationModel<CashierDetailDto> {

	Long id;

	String name;

	BigDecimal started;

	BigDecimal balance;

	public CashierDetailDto(Cashier cashier) {
		this.id = cashier.getId();
		this.name = cashier.getName();
		this.started = cashier.getStarted();
		this.balance = cashier.getBalance();

		Link selfLink = linkTo(methodOn(CashierController.class).findById(id)).withSelfRel();
		this.add(selfLink);
	}

}
