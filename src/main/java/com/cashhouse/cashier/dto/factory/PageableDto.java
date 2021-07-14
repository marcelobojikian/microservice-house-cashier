package com.cashhouse.cashier.dto.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableDto {
	
	public Page<?> asPage(Pageable pageable);
	
	public boolean isPartialPage();

}
