package com.cashhouse.cashier.integration.cashier;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class PutTest extends SampleRequest {

//	@Test
//	@Sql(value={ "classpath:schema.sql","classpath:data.sql","classpath:scene.sql"},  executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
//	public void update_OK_ADMIN_Dashboard() throws Exception {
//
////		loginWith(MARCELO);
//
//		// @formatter:off
//		body()
//			.add("name", "New Rent & Bills")
//			.add("started", new BigDecimal("0.50"))
//			.add("balance", new BigDecimal("32.99"))
//			.add("owner", "2");
//		
//		put("/cashiers/1")
////				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", is(1)))
//				.andExpect(jsonPath("$.name", is("New Rent & Bills")))
//				.andExpect(jsonPath("$.started", is(0.50)))
//				.andExpect(jsonPath("$.balance", is(32.99)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void update_invalid_id_NotFound() throws Exception {
//
////		loginWith(MARCELO);
//
//		// @formatter:off
//		body()
//			.add("name", "New Name")
//			.add("started", new BigDecimal("44.32"))
//			.add("balance", new BigDecimal("12.42"));
//		
//		put("/cashiers/999")
//				.andExpect(status().isForbidden());
//        // @formatter:on
//
//	}
//
//	@Test
//	public void guest_update_invalid_id_InOther_Dashboard() throws Exception {
//
////		loginWith(MARCELO);
//
//		// @formatter:off
//		body()
//			.add("name", "New Name")
//			.add("started", new BigDecimal("44.32"))
//			.add("balance", new BigDecimal("12.42"));
//		
//		put("/cashiers/3")
//				.andExpect(status().isForbidden());
//        // @formatter:on
//
//	}

}
