package com.cashhouse.cashier.dto.response.cashier;

import org.springframework.data.domain.Page;

import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.model.Cashier;

public class CashierListDto extends SimpleListDto<CashierDetailDto, Cashier> {
	
	public CashierListDto(Page<Cashier> cashiers) {
		super(cashiers);
	}

	@Override
	public CashierDetailDto getContent(Cashier cashiers) {
		return new CashierDetailDto(cashiers);
	}

}
