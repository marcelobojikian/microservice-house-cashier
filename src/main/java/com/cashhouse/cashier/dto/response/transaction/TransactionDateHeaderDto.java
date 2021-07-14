package com.cashhouse.cashier.dto.response.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.data.domain.Page;

import com.cashhouse.cashier.dto.factory.type.GroupListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionDateHeaderDto extends GroupListDto<TransactionDetailDto, Transaction> {
	
	private DateTimeFormatter formatter;
	
	public TransactionDateHeaderDto(Page<Transaction> transactions, FormatStyle style, Locale locale) {
		super(transactions);
		this.formatter = DateTimeFormatter.ofLocalizedDate(style).withLocale(locale);
	}

	@Override
	public String getHeader(Transaction transaction) {
		LocalDateTime createdDate = transaction.getCreatedDate();
		return createdDate.format(formatter).toString();
	}

	@Override
	public TransactionDetailDto getContent(Transaction transaction) {
		return new TransactionDetailDto(transaction);
	}

}
