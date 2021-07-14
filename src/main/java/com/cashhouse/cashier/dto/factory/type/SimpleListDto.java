package com.cashhouse.cashier.dto.factory.type;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cashhouse.cashier.dto.factory.PageableDto;

public abstract class SimpleListDto<T, L> implements PageableDto {

	private Page<L> list;

	public SimpleListDto(Page<L> list) {
		this.list = list;
	}

	public abstract T getContent(L l);

	@Override
	public Page<?> asPage(Pageable pageable) {
		List<T> dtoList = new ArrayList<>();
		list.forEach(l -> {
			dtoList.add(getContent(l));
		});

		Long totalElements = list.getTotalElements();
		return new PageImpl<>(dtoList, pageable, totalElements.intValue());
	}

	@Override
	public boolean isPartialPage() {
		return list.getNumberOfElements() < list.getTotalElements();
	}

}
