package com.cashhouse.cashier.dto.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableDto<T> {
	
	public Page<? extends Object> asPage(Page<T> list, Pageable pageable);

}
