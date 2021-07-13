package com.cashhouse.cashier.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseEntityErrorDto {
	
	private String field;
	private String message;

}
