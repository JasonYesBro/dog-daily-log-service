package com.dogdailylog.pethotel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PetHotelControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	void testHotelList() throws Exception {
//		mockMvc.perform(post("/user/sign_in").param("email", "tmdgus5611@gmail.com").param("password", "111111"))
//		.andExpect(status().isOk());
		mockMvc.perform(get("/hotel/list_view"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/user/sign_in_view"));
	}

}
