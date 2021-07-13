package com.cashhouse.cashier.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	public Optional<Transaction> findById(@Param("id") long id);

	public List<Transaction> findByCashier(@Param("cashier") Cashier cashier);

}
