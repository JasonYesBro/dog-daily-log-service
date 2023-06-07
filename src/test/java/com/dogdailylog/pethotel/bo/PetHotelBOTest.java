package com.dogdailylog.pethotel.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dogdailylog.pethotel.model.PetHotel;

@SpringBootTest
class PetHotelBOTest {

	@Autowired
	PetHotelBO petHotelBO;
	
	@Test
	void 애견호텔() {
		// given
		int id = 25;
		
		// when
		PetHotel petHotel = petHotelBO.getPetHotelById(id);
		
		//then
		assertEquals(id, petHotel.getId());
	}
	
	@Test
	void 애견호텔리스트() {
		// given
		int size = 24;
		
		// when
		int list = petHotelBO.getPetHotelList().size();
		
		//then
		assertEquals(size, list);
	}

}
