package com.dogdailylog.pethotel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetHotelRestController {
	
	@GetMapping("/pethotel") 
	public Map<String, Object> petHotelList() throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		PetHotelAPI api = new PetHotelAPI();
		
		try {
			String list = api.petHotelList();
			
			JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(list);

			result.put("list", jsonObject.get("data"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
}
