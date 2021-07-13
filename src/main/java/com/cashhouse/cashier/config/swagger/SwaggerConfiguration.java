package com.cashhouse.cashier.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.model.Transaction;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public Docket cashierApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cashhouse.cashier"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Cashier.class, Transaction.class);
	}

}
