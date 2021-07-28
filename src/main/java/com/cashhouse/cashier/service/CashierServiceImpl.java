package com.cashhouse.cashier.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.repository.CashierRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CashierServiceImpl implements CashierService {

	private static final String CASHIER_NOT_FOUND = "cashier.not.found";

	private CashierRepository cashierRepository;

	@Autowired
	public CashierServiceImpl(CashierRepository cashierRepository) {
		this.cashierRepository = cashierRepository;
	}

	@Override
	public Cashier findById(long id) {
		return cashierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CASHIER_NOT_FOUND));
	}

	@Override
	public Page<Cashier> findAll(Pageable pageable) {
		return cashierRepository.findAll(pageable);
	}

	@Override
	public Cashier create(Cashier cashier) {

		log.info(String.format("Creating Cashier %s", cashier.getName()));

		return cashierRepository.save(cashier);
	}

	@Override
	public Cashier update(long id, Cashier cashier) {

		Cashier entity = cashierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CASHIER_NOT_FOUND));

		log.info(String.format("Cashier %s changing ... ", entity.getName()));
		log.info(String.format("Name[%s], Started[%s], Balance[%s]", cashier.getName(), cashier.getStarted(),
				cashier.getBalance()));

		entity.setName(cashier.getName());
		entity.setStarted(cashier.getStarted());
		entity.setBalance(cashier.getBalance());

		return cashierRepository.save(entity);
	}

	@Override
	@Transactional
	public void delete(long id) {

		Cashier cashier = cashierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CASHIER_NOT_FOUND));

		log.info(String.format("Deleting Cashier %s", cashier.getName()));

		cashierRepository.delete(cashier);

		log.info("Delete sucess");

	}

}
