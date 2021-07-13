package com.cashhouse.cashier.converter;

import org.springframework.core.convert.converter.Converter;

import com.cashhouse.cashier.model.Transaction.Action;

public class ActionToEnumConverter implements Converter<String, Action> {

	@Override
	public Action convert(String source) {
		return Action.valueOf(source.toUpperCase());
	}

}
