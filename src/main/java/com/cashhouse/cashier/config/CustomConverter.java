package com.cashhouse.cashier.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cashhouse.cashier.converter.ActionToEnumConverter;
import com.cashhouse.cashier.converter.StatusToEnumConverter;

@Configuration
public class CustomConverter implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ActionToEnumConverter());
		registry.addConverter(new StatusToEnumConverter());
	}

}
