package com.cashhouse.cashier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	private Cashier cashier;

	@Setter(value = AccessLevel.NONE)
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public enum Status {
		SENDED, FINISHED;
	}

	@Column
	@Enumerated(EnumType.STRING)
	private Action action;

	public enum Action {
		DEPOSIT {
			@Override
			public void commit(Cashier cashier, BigDecimal value) {
				cashier.doDeposit(value);
			}
		},
		WITHDRAW {
			@Override
			public void commit(Cashier cashier, BigDecimal value) {
				cashier.doWithdraw(value);
			}
		};

		abstract void commit(Cashier cashier, BigDecimal value);
	}

	@Column
	private BigDecimal value;

	@Setter(value = AccessLevel.PACKAGE)
	@Column(name = "created_at")
	private LocalDateTime createdDate;

	@Setter(value = AccessLevel.NONE)
	@Column(name = "updated_at")
	private LocalDateTime updatedDate;

	public Transaction() {
		this.status = Status.SENDED;
	}

	@PrePersist
	void prePersist() {
		createdDate = LocalDateTime.now();
	}

	@PreUpdate
	void preUpdate() {
		updatedDate = LocalDateTime.now();
	}

	public boolean isSended() {
		return status.equals(Status.SENDED);
	}

	public boolean isFinished() {
		return status.equals(Status.FINISHED);
	}

	public void commit() {

		if (isSended()) {
			log.info(String.format("Action %s in the Cashier %s with current balance %s", action, cashier.getId(), cashier.getBalance()));
			action.commit(cashier, value);
			log.info(String.format("Apply %s. Changed balance to %s", value, cashier.getBalance()));
			this.status = Status.FINISHED;
		} else {
			log.info(String.format(String.format("Invalid operation, Transaction %s with status equal to %s", id, status)));
			throw new IllegalStateException("transaction.status.invalid.operation");
		}

	}

}
