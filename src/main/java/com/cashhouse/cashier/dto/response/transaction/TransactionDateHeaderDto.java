package com.cashhouse.cashier.dto.response.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import com.cashhouse.cashier.dto.factory.type.GroupListDto;
import com.cashhouse.cashier.model.Transaction;

public class TransactionDateHeaderDto extends GroupListDto<TransactionDetailDto, Transaction> {
	
	private DateTimeFormatter formatter;
	
	public TransactionDateHeaderDto() {
		this(FormatStyle.LONG, LocaleContextHolder.getLocale());
	}
	
	public TransactionDateHeaderDto(Locale locale) {
		this(FormatStyle.LONG, locale);
	}
	
	public TransactionDateHeaderDto(FormatStyle formatStyle, Locale locale) {
		this.formatter = DateTimeFormatter.ofLocalizedDate(formatStyle);
		this.formatter.withLocale(locale);
	}

	public void setFormatter(Locale locale) {
		this.formatter.withLocale(locale);
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
