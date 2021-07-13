package com.cashhouse.cashier.dto.output;

import java.math.BigDecimal;

import com.cashhouse.cashier.model.Cashier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CashierDetailDto {

	Long id;

	String name;

	BigDecimal started;

	BigDecimal balance;

	public CashierDetailDto(Cashier cashier) {
		this.id = cashier.getId();
		this.name = cashier.getName();
		this.started = cashier.getStarted();
		this.balance = cashier.getBalance();
	}

}
