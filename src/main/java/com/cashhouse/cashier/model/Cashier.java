package com.cashhouse.cashier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Cashier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column
	private String name;
	@Column
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal started;
	@Column
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal balance;

	@OneToMany(mappedBy = "cashier", cascade = {CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Transaction> transactions;

	public Cashier(String name, BigDecimal started, BigDecimal balance) {
		super();
		this.name = name;
		this.started = started;
		this.balance = balance;
		this.transactions = new ArrayList<>();
	}
	
	public List<Transaction> getTransactions(){
		return Collections.unmodifiableList(transactions);
	}

	void doDeposit(BigDecimal value) {
		this.balance = this.balance.add(value);
	}

	void doWithdraw(BigDecimal value) {
		this.balance = this.balance.subtract(value);
	}

}
