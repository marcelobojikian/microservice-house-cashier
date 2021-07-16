package com.cashhouse.cashier.dto.response.cashier;

import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.model.Cashier;

public class CashierListDto extends SimpleListDto<CashierDetailDto, Cashier> {

	@Override
	public CashierDetailDto getContent(Cashier cashiers) {
		return new CashierDetailDto(cashiers);
	}

}
