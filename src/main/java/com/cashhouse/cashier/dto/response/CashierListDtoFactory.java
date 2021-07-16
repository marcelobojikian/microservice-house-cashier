package com.cashhouse.cashier.dto.response;

import com.cashhouse.cashier.dto.factory.ResponseListDtoFactory;
import com.cashhouse.cashier.dto.factory.type.SimpleListDto;
import com.cashhouse.cashier.dto.response.cashier.CashierDetailDto;
import com.cashhouse.cashier.model.Cashier;

public class CashierListDtoFactory extends ResponseListDtoFactory<CashierDetailDto, Cashier> {

	public CashierListDtoFactory(SimpleListDto<CashierDetailDto, Cashier> defaultList) {
		super(defaultList);
	}

}
