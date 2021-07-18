package com.cashhouse.cashier.controller;

import static com.cashhouse.cashier.util.EntityFactory.createCashier;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import com.cashhouse.cashier.config.validation.CustomRestValidationHandler;
import com.cashhouse.cashier.model.Cashier;
import com.cashhouse.cashier.service.CashierService;
import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
class CashierControllerTest extends SampleRequest {

	private static final EntityNotFoundException cashierNotFound = new EntityNotFoundException("cashier.not.found");

	@MockBean
	private CustomRestValidationHandler restValidationHandler;

	@MockBean
	private CashierService cashierService;
	
	/**
	 * methods findById
	 */

	@Test
	void whenFindById_thenReturnEntityObject() throws Exception {

		Cashier entity = createCashier(3l, "Rent & Clean", new BigDecimal("12.45"), new BigDecimal("3.11"));
		when(cashierService.findById(3l)).thenReturn(entity);

		// @formatter:off
		get("/cashiers/3")
			.andExpect(content().contentType(MediaTypes.HAL_JSON))
	        .andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(3)))
			.andExpect(jsonPath("$.name", is("Rent & Clean")))
			.andExpect(jsonPath("$.started", is(12.45)))
			.andExpect(jsonPath("$.balance", is(3.11)));
        // @formatter:on

	}
	
	@Test
	void whenFindById_thenReturnResponseStatusNotFound() throws Exception {
		
		when(cashierService.findById(any(Long.class))).thenThrow(cashierNotFound);
		
		get("/cashiers/999").andExpect(status().isNotFound());
		
	}
	
	/**
	 * methods findAll
	 */

	@Test
	void whenFindAll_thenReturnNoContent() throws Exception {

		Page<Cashier> page = new PageImpl<>(Collections.emptyList());

		when(cashierService.findAll(any())).thenReturn(page);

		get("/cashiers").andExpect(status().isNoContent());

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusOk() throws Exception {

		Cashier energy = createCashier(3l, "Energy", new BigDecimal("12.45"), new BigDecimal("3.11"));

		Page<Cashier> page = new PageImpl<>(Arrays.asList(energy));

		when(cashierService.findAll(any())).thenReturn(page);

		// @formatter:off
		get("/cashiers")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk());
        // @formatter:on

	}

	@Test
	void whenFindAll_thenReturnListObjectsStatusPartialContent() throws Exception {

		Cashier energy = createCashier(3l, "Energy", new BigDecimal("12.45"), new BigDecimal("3.11"));
		
		Page<Cashier> page = new PageImpl<>(Arrays.asList(energy), PageRequest.of(1, 8), 20);

		when(cashierService.findAll(any())).thenReturn(page);

		// @formatter:off		
		get("/cashiers?size=2")
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isPartialContent());
        // @formatter:on

	}
	
	/**
	 * methods create
	 */

	void whenCreateWithoutBody_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithNullName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithNullBalance_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidStarted_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenCreateWithInvalidBalance_thenReturnResponseStatusBadRequest() throws Exception { }

	@Test
	void whenCreateWithParameterStartedIsNull_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Cashier entity = createCashier(3l, "Post Test Cashier", new BigDecimal("11.23"), new BigDecimal("11.23"));
		when(cashierService.create(any())).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "Post Test Cashier")
			.add("balance", new BigDecimal("11.23"));

		post("/cashiers")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/cashiers/3"))
			.andExpect(jsonPath("$.name", is("Post Test Cashier")))
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(11.23)));
        // @formatter:on
		
	}
	
	@Test
	void whenCreate_thenReturnEntityObjectAndRedirectUrl() throws Exception {

		Cashier entity = createCashier(3l, "Post Test Cashier", new BigDecimal("11.23"), new BigDecimal("123.23"));
		when(cashierService.create(any())).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "Post Test Cashier")
			.add("started", new BigDecimal("11.23"))
			.add("balance", new BigDecimal("123.23"));

		post("/cashiers")
			.andExpect(status().isCreated())
			.andExpect(redirectedUrl("/cashiers/3"))
			.andExpect(jsonPath("$.name", is("Post Test Cashier")))
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(123.23)));
        // @formatter:on
		
	}
	
	/**
	 * methods update
	 */
	
	void whenUpdateWithoutId_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithoutBody_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullStater_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithNullBalance_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidStarted_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenUpdateWithInvalidBalance_thenReturnResponseStatusBadRequest() throws Exception { }

	@Test
	void whenUpdate_thenReturnEntityObject() throws Exception {

		Cashier entity = createCashier(3l, "UPDATE Test Cashier", new BigDecimal("11.23"), new BigDecimal("123.23"));
		when(cashierService.update(any(Long.class), any(Cashier.class))).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "UPDATE Test Cashier")
			.add("started", new BigDecimal("11.23"))
			.add("balance", new BigDecimal("123.23"));

		put("/cashiers/3")
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("UPDATE Test Cashier")))
			.andExpect(jsonPath("$.started", is(11.23)))
			.andExpect(jsonPath("$.balance", is(123.23)));
        // @formatter:on
		
	}
	
	/**
	 * methods path
	 */

	void whenPatchWithoutBody_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenPatchWithNullName_thenReturnResponseStatusBadRequest() throws Exception { }
	void whenPatchWithBlankName_thenReturnResponseStatusBadRequest() throws Exception { }

	@Test
	void whenPatchWithInvalidId_thenReturnResponseStatusNotFound() throws Exception {
		
		when(cashierService.findById(any(Long.class))).thenThrow(cashierNotFound);

		body()
			.add("name", "UPDATE PARTIAL Test Cashier");
		
		patch("/cashiers/999").andExpect(status().isNotFound());
		
	}

	@Test
	void whenPatch_thenReturnEntityObject() throws Exception {

		Cashier entity = createCashier(3l, "Put Test Cashier", new BigDecimal("97.01"), new BigDecimal("1.55"));
		when(cashierService.findById(any(Long.class))).thenReturn(entity);
		when(cashierService.update(any(Long.class), any(Cashier.class))).thenReturn(entity);

		// @formatter:off
		body()
			.add("name", "UPDATE PARTIAL Test Cashier");

		patch("/cashiers/3")
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("UPDATE PARTIAL Test Cashier")))
			.andExpect(jsonPath("$.started", is(97.01)))
			.andExpect(jsonPath("$.balance", is(1.55)));
        // @formatter:on
		
	}
	
	/**
	 * methods delete
	 */

	@Test
	void whenDeleteWithInvalidId_thenReturnResponseStatusNotFound() throws Exception {
		
		doThrow(cashierNotFound).when(cashierService).delete(any(Long.class));
		
		delete("/cashiers/999").andExpect(status().isNotFound());
		
	}
	
	@Test
	void whenDelete_thenReturnEntityObject() throws Exception {
		
		doNothing().when(cashierService).delete(any(Long.class));
		
		delete("/cashiers/1").andExpect(status().isOk());
		
	}

}
