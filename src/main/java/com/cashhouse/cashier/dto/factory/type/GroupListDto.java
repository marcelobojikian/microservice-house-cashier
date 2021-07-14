package com.cashhouse.cashier.dto.factory.type;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cashhouse.cashier.dto.factory.PageableDto;

public abstract class GroupListDto<T, L> implements PageableDto {
	
	private Page<L> list;
	private Map<String, Collection<T>> dtoMap;
	
	public GroupListDto(Page<L> list) {
		this.list = list;
		this.dtoMap = new LinkedHashMap<>();
	}
	
	public void add(T t, String header) {
		if (!dtoMap.containsKey(header)) {
			dtoMap.put(header, new LinkedList<>());
		}
		dtoMap.get(header).add(t);
	}
	
	public abstract String getHeader(L l);
	
	public abstract T getContent(L l);

	@Override
	public boolean isPartialPage() {
		return list.getNumberOfElements() < list.getTotalElements();
	}
	
	@Override
	public Page<?> asPage(Pageable pageable){

		list.forEach(l -> {
			String header = getHeader(l);
			add(getContent(l), header);
		});
		
		Long totalElements = list.getTotalElements();
		List<Object> asList = Arrays.asList(dtoMap);
		
		return new PageImpl<Object>(asList, pageable, totalElements.intValue());
		
	}

}
