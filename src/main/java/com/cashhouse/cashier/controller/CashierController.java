package com.cashhouse.cashier.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cashhouse.cashier.dto.factory.PageableDto;
import com.cashhouse.cashier.dto.request.cashier.CreateCashier;
import com.cashhouse.cashier.dto.request.cashier.EntityCashier;
import com.cashhouse.cashier.dto.request.cashier.UpdateCashier;
import com.cashhouse.cashier.dto.response.CashierListDtoFactory;
import com.cashhouse.cashier.dto.response.cashier.CashierDetailDto;
import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.service.CashierService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/cashiers")
public class CashierController {

	@Autowired
	private CashierService cashierService;

	@Autowired
	private CashierListDtoFactory factory;

	@GetMapping("")
	@ApiOperation(value = "Return list with all cashiers", response = CashierDetailDto[].class)
	public ResponseEntity<Page<? extends Object>> findAll(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.ASC) Pageable pageable) {

		Page<Cashier> cashiers = cashierService.findAll(pageable);

		if (cashiers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		PageableDto<Cashier> dto = factory.getListDto(pageable);
		
		boolean isPartialPage = cashiers.getNumberOfElements() < cashiers.getTotalElements();
		HttpStatus httpStatus = isPartialPage ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK;
		
		return new ResponseEntity<>(dto.asPage(cashiers, pageable), httpStatus);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Return cashier entity by id", response = CashierDetailDto.class)
	public CashierDetailDto findById(@PathVariable Long id) {
		Cashier cashier = cashierService.findById(id);
		return new CashierDetailDto(cashier);
	}

	@PostMapping("")
	@ApiOperation(value = "Return cashier entity created", response = CashierDetailDto.class)
	public ResponseEntity<CashierDetailDto> create(@RequestBody @Valid CreateCashier dto,
			UriComponentsBuilder uriBuilder) {

		Cashier cashier = dto.toEntity();

		Cashier entity = cashierService.create(cashier);

		URI uri = uriBuilder.path("/api/v1/cashiers/{id}").buildAndExpand(entity.getId()).toUri();
		return ResponseEntity.created(uri).body(new CashierDetailDto(entity));

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Return cashier entity updated", response = CashierDetailDto.class)
	public CashierDetailDto update(@PathVariable Long id, @RequestBody @Valid EntityCashier dto) {
		Cashier entity = cashierService.update(id, dto.toEntity());
		return new CashierDetailDto(entity);
	}

	@PatchMapping("/{id}")
	@ApiOperation(value = "Return cashier entity updated", response = CashierDetailDto.class)
	public CashierDetailDto patch(@PathVariable Long id, @RequestBody @Valid UpdateCashier dto) {

		Cashier entity = cashierService.findById(id);

		String name = dto.getName();
		entity.setName(name);

		cashierService.update(id, entity);

		return new CashierDetailDto(entity);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Return status OK when deleted")
	public void detele(@PathVariable Long id) {
		cashierService.delete(id);
	}

}
