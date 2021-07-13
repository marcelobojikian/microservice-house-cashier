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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@RequiredArgsConstructor
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	private Cashier cashier;

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

	@Column(name = "created_at")
	private LocalDateTime createdDate;

	@Column(name = "updated_at")
	private LocalDateTime updatedDate;

	@PrePersist
	public void prePersist() {
		createdDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedDate = LocalDateTime.now();
	}

	public boolean isSended() {
		return status != null && status.equals(Status.SENDED);
	}

	public boolean isFinished() {
		return status != null && status.equals(Status.FINISHED);
	}

	public void commit() {

		if (isSended()) {
			log.info(String.format("Action %s in a Cashier %s current balance %s", action, cashier.getId(), cashier.getBalance()));
			action.commit(cashier, value);
			log.info(String.format("Apply %s. Changed balance to %s", value, cashier.getBalance()));
			setStatus(Status.FINISHED);
		} else {
			log.info(String.format(String.format("Invalid operation, Transaction %s with status equal to %s", id, status)));
			throw new IllegalStateException("transaction.status.invalid.operation");
		}

	}

}
