package com.dogdailylog.kakao.bo;

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
class KakaoLoginBOTest {
	@Autowired
	KakaoLoginBO kakaoLoginBO;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void 카카오로그인_URL호출_테스트() throws Exception {
		// given
		String url = kakaoLoginBO.connect();
		
		// then
		mockMvc.perform(get("/kakao/oauth"))
		.andExpect(redirectedUrl(url))
		.andExpect(status().is3xxRedirection());
	}

}
