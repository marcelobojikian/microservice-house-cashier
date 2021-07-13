package com.cashhouse.cashier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cashhouse.cashier.model.Cashier;

public interface CashierRepository extends JpaRepository<Cashier, Long> {

	public Optional<Cashier> findById(@Param("id") long id);

}
