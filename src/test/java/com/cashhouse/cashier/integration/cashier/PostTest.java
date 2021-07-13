package com.cashhouse.cashier.integration.cashier;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class PostTest extends SampleRequest {
	
	// TODO: Refatorar a classe SampleRequest.getUri(...) 
	// e fazer fazer um metodo para alterar o idioma no header

//	@Test
//	public void save_WithStarted_OK() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", new BigDecimal("11.23"))
//			.add("balance", new BigDecimal("123.23"));
//
//		ResultActions ra = post("/cashiers");
//			  ra.andExpect(status().isCreated())
//				.andExpect(redirectedUrl("/cashiers/"+getJsonProperty(ra, "$.id")))
//				.andExpect(jsonPath("$.name", is("Post Test Cashier")))
//				.andExpect(jsonPath("$.started", is(11.23)))
//				.andExpect(jsonPath("$.balance",is(123.23)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_invalid_balance_Fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", new BigDecimal("11.23"))
//			.add("balance", "AA123");
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());;
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_invalid_MAX_balance_Fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", new BigDecimal("11.23"))
//			.add("balance", "12345678901.22");
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());;
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_invalid_MAX_started_Fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", "12345678901.22")
//			.add("balance", new BigDecimal("11.23"));
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());;
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_invalid_started_Fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", "AA123")
//			.add("balance", new BigDecimal("11.23"));
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());;
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_WithoutStarted_OK() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("balance", new BigDecimal("198.44"));
//
//		post("/cashiers")
//				.andExpect(status().isCreated())
//				.andExpect(jsonPath("$.name", is("Post Test Cashier")))
//				.andExpect(jsonPath("$.started", is(198.44)))
//				.andExpect(jsonPath("$.balance",is(198.44)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_without_balance_fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", new BigDecimal("11.23"));
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());
//        // @formatter:on
//
//	}
//
//	@Test
//	public void save_without_name_fail() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		body()
//			.add("started", new BigDecimal("11.23"))
//			.add("balance", new BigDecimal("123.23"));
//
//		post("/cashiers")
//				.andExpect(status().isBadRequest());
//        // @formatter:on
//
//	}

//	@Test
//	public void guest_save_Forbidden_fail() throws Exception {
//
////		loginWith(GRETCHEN).dashboard(MARCELO);
//
//		// @formatter:off
//		body()
//			.add("name", "Post Test Cashier")
//			.add("started", new BigDecimal("11.23"))
//			.add("balance", new BigDecimal("123.23"));
//
//		post("/cashiers")
//				.andExpect(status().isForbidden());
//        // @formatter:on
//
//	}

}
