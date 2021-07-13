package com.cashhouse.cashier.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.cashhouse.cashier.model.Transaction;
import com.cashhouse.cashier.model.Transaction.Action;
import com.cashhouse.cashier.model.Transaction.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionDetailDto implements Comparable<TransactionDetailDto> {
	
	@JsonIgnore
	private String groupName;

	Long id;

	String cashier;

	Status status;

	Action action;

	BigDecimal value;

	LocalDateTime createdDate;

	LocalDateTime updatedDate;

	public static TransactionDetailDto convert(Transaction transaction, Locale locale) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale);
		return convert(transaction, formatter);
	}

	public static TransactionDetailDto convert(Transaction transaction) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		return convert(transaction, formatter);
	}

	public static TransactionDetailDto convert(Transaction transaction, DateTimeFormatter formatter) {
		LocalDateTime key = transaction.getCreatedDate();
		// @formatter:off
		return new TransactionDetailDto(
				key.format(formatter).toString(),
				transaction.getId(),
				transaction.getCashier().getName(),
				transaction.getStatus(),
				transaction.getAction(),
				transaction.getValue(),
				transaction.getCreatedDate(),
				transaction.getUpdatedDate());
		// @formatter:on
	}

	public static Map<String, Collection<TransactionDetailDto>> convert(Page<Transaction> transactions) {

		Map<String, Collection<TransactionDetailDto>> dtoMap = new LinkedHashMap<>();
		transactions.forEach(transaction -> {
			TransactionDetailDto dto = TransactionDetailDto.convert(transaction);
			if (!dtoMap.containsKey(dto.getGroupName())) {
				dtoMap.put(dto.getGroupName(), new LinkedList<>());
			}
			Collection<TransactionDetailDto> collections = dtoMap.get(dto.getGroupName());
			collections.add(dto);
		});
		
		return dtoMap;
		
	}

	public static Map<String, Collection<TransactionDetailDto>> convert(Page<Transaction> transactions, Locale locale) {

		Map<String, Collection<TransactionDetailDto>> dtoMap = new LinkedHashMap<>();
		transactions.forEach(transaction -> {
			TransactionDetailDto dto = TransactionDetailDto.convert(transaction, locale);
			if (!dtoMap.containsKey(dto.getGroupName())) {
				dtoMap.put(dto.getGroupName(), new LinkedList<>());
			}
			Collection<TransactionDetailDto> collections = dtoMap.get(dto.getGroupName());
			collections.add(dto);
		});
		
		return dtoMap;
		
	}

	@Override
	public int compareTo(TransactionDetailDto dto) {
		return createdDate.compareTo(dto.getCreatedDate());
	}

}
