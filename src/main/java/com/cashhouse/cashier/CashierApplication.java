package com.cashhouse.cashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.cashhouse.cashier.dto.response.CashierListDtoFactory;
import com.cashhouse.cashier.dto.response.TransactionListDtoFactory;
import com.cashhouse.cashier.dto.response.cashier.CashierListDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionCashierHeaderDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionDateHeaderDto;
import com.cashhouse.cashier.dto.response.transaction.TransactionListDto;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
public class CashierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashierApplication.class, args);
	}

	@Bean
	public TransactionListDtoFactory getTransactionListDtoFactory() {
		TransactionListDtoFactory factory = new TransactionListDtoFactory(new TransactionListDto());
		factory.addField("createdDate", new TransactionDateHeaderDto());
		factory.addField("cashier", new TransactionCashierHeaderDto());
		return factory;
	}

	@Bean
	public CashierListDtoFactory getCashierListDtoFactory() {
		return new CashierListDtoFactory(new CashierListDto());
	}

}
