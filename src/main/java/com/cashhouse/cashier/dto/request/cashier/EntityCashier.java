package com.cashhouse.cashier.dto.request.cashier;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.cashhouse.cashier.model.Cashier;

import lombok.Setter;

@Setter
public class EntityCashier {

	@NotEmpty
	String name;

	@NotNull
	@NumberFormat(style = Style.CURRENCY)
	@Digits(integer = 10, fraction = 2)
	BigDecimal started;

	@NotNull
	@NumberFormat(style = Style.CURRENCY)
	@Digits(integer = 10, fraction = 2)
	BigDecimal balance;

	public Cashier toEntity() {
		return new Cashier(name, started, balance);
	}

}
