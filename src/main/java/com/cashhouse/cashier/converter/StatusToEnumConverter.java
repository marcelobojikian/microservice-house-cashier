package com.cashhouse.cashier.converter;

import org.springframework.core.convert.converter.Converter;

import com.cashhouse.cashier.model.Transaction.Status;

public class StatusToEnumConverter implements Converter<String, Status> {

	@Override
	public Status convert(String source) {
		return Status.valueOf(source.toUpperCase());
	}

}
