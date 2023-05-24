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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PetHotelController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/map")
	public String petHotelView(Model model) throws ParseException {
		List<Map<String, Object>> hotelList = new ArrayList<>();
		
		PetHotelAPI api = new PetHotelAPI();
		
		try {
			String list = api.petHotelList();
			
			JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(list);
	        JSONArray jsonArray = (JSONArray) parser.parse(jsonObject.get("data").toString());
	        
	        for(Object hotel : jsonArray) {
	        	String hotelStr[] = hotel.toString().split(",");
	        	
	        	String hotelName = hotelStr[0].split(":")[1].toString().replaceAll("\\\"","");
	        	String hotelTel = hotelStr[2].split(":")[1].toString().replaceAll("\\\"","");
	        	String hotelAddress = hotelStr[4].split(":")[1].toString().replaceAll("\\\"","");
	        	
	        	Map<String, Object> map = new HashMap<>();
	        	
	        	map.put("name", hotelName);
	        	map.put("tel", hotelTel);
	        	map.put("address", hotelAddress);
	        	
	        	//logger.info("%%%%%%%%%%%% hotel %%%%%%%%%%%%% {}", map);
	        	hotelList.add(map);
	        }
	        
			model.addAttribute("hotelList", hotelList);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("view", "pethotel/petHotelList");
		
		return "template/layout";
	}
}
