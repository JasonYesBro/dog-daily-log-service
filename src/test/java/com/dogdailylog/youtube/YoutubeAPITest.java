package com.dogdailylog.youtube;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class YoutubeAPITest {
	
	@Autowired
	YoutubeAPI youtubeAPI;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@Order(1)
	void 유튜브API테스트() throws Exception {
		
		mockMvc.perform(get("/youtube/search"))
		.andExpect(status().is2xxSuccessful())
		.andReturn();
		
	}
	
	@Test
	@Order(2)
	void 유튜브검색테스트() throws IOException, ParseException {

		//given
		String searchParam = "센과 치히로의 행방불명";

		// then
//		assertNotNull(youtubeAPI.search(searchParam));
	}

}
