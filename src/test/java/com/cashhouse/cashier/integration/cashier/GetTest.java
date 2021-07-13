package com.cashhouse.cashier.integration.cashier;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class GetTest extends SampleRequest {

//	@Test
//	public void find_Id_OK() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		get("/cashiers/3")
////		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//		        .andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", is(3)))
//				.andExpect(jsonPath("$.name", is("Rent & Clean")))
//				.andExpect(jsonPath("$.started", is(12.45)))
//				.andExpect(jsonPath("$.balance", is(3.11)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void guest_find_Id_OK() throws Exception {
//
////		loginWith(GRETCHEN).dashboard(JEAN);
//
//		// @formatter:off
//		get("/cashiers/3")
////		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//		        .andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", is(3)))
//				.andExpect(jsonPath("$.name", is("Rent & Clean")))
//				.andExpect(jsonPath("$.started", is(12.45)))
//				.andExpect(jsonPath("$.balance", is(3.11)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void find_all_OK() throws Exception {
//
////		loginWith(MARCELO);
//
//		// @formatter:off
//		get("/cashiers")
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasSize(2)))
//				.andExpect(jsonPath("$[0].id", is(1)))
//				.andExpect(jsonPath("$[0].name", is("Energy & bin")))
//				.andExpect(jsonPath("$[0].started", is(0.0)))
//				.andExpect(jsonPath("$[0].balance",is(32.54)))
//				.andExpect(jsonPath("$[1].id", is(2)))
//				.andExpect(jsonPath("$[1].name", is("Geral")))
//				.andExpect(jsonPath("$[1].started", is(23.0)))
//				.andExpect(jsonPath("$[1].balance", is(120.0)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void firstAccess_find_all_OK() throws Exception {
//
////		loginWith(GRETCHEN);
//
//		// @formatter:off
//		get("/cashiers")
//				.andExpect(status().isNoContent());
//        // @formatter:on
//
//	}
//
//	@Test
//	public void guest_find_all_OK() throws Exception {
//
////		loginWith(BIRO).dashboard(MARCELO);
//
//		// @formatter:off
//		get("/cashiers")
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasSize(2)))
//				.andExpect(jsonPath("$[0].id", is(1)))
//				.andExpect(jsonPath("$[0].name", is("Energy & bin")))
//				.andExpect(jsonPath("$[0].started", is(0.0)))
//				.andExpect(jsonPath("$[0].balance",is(32.54)))
//				.andExpect(jsonPath("$[1].id", is(2)))
//				.andExpect(jsonPath("$[1].name", is("Geral")))
//				.andExpect(jsonPath("$[1].started", is(23.0)))
//				.andExpect(jsonPath("$[1].balance", is(120.0)));
//        // @formatter:on
//
//	}
//
//	@Test
//	public void find_notFound() throws Exception {
//
////		loginWith(JEAN);
//
//		// @formatter:off
//		get("/cashiers/99")
//				.andExpect(status().isForbidden());
//        // @formatter:on
//
//	}
//
//	@Test
//	public void guest_find_notFound() throws Exception {
//
////		loginWith(GRETCHEN).dashboard(JEAN);
//
//		// @formatter:off
//		get("/cashiers/999")
//				.andExpect(status().isForbidden());
//        // @formatter:on
//
//	}

}
