package com.dogdailylog.pethotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.map.DistanceMatrixAPI;

@RestController
public class PetHotelRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<String, Object> petHotelList() throws ParseException {
		
		Map<String, Object> result = new HashMap<>();
		List<Object> listHotel = new ArrayList<>();
		PetHotelAPI api = new PetHotelAPI();
		
		try {
			String list = api.petHotelList();
			
			JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(list);
	        
	        JSONArray jsonArray = (JSONArray) parser.parse(jsonObject.get("data").toString());
	        
	        for(Object hotel : jsonArray) {
	        	logger.info("%%%%%%%%%%%% hotel %%%%%%%%%%%%% {}", hotel);
	        }
			
			result.put("list", jsonArray);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
