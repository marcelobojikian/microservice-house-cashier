package com.cashhouse.cashier.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cashhouse.cashier.model.Cashier;

public interface CashierService {

	public Cashier findById(long id);

	public Page<Cashier> findAll(Pageable pageable);

	public Cashier create(Cashier cashier);

	public Cashier update(long id, Cashier cashier);

	public void delete(long id);

}
