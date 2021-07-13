package com.cashhouse.cashier.integration.cashier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cashhouse.cashier.service.CashierService;
import com.cashhouse.cashier.util.SampleRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteTest extends SampleRequest {

//	@MockBean
//	private CashierService cashierService;
//
//	@Test
//	public void delete_OK() throws Exception {
//
//		doNothing().when(cashierService).delete(anyLong());
//
//		// @formatter:off
//		delete("/cashiers/3")
//				.andExpect(status().isOk());
//        // @formatter:on
//
//	}
//
//	@Test
//	public void delete_CashierNotExist() throws Exception {
//
//		doThrow(RuntimeException.class).when(cashierService).delete(anyLong());
//
//		// @formatter:off
//		delete("/cashiers/4")
//				.andExpect(status().isNotFound());
//        // @formatter:on
//
//	}

}
