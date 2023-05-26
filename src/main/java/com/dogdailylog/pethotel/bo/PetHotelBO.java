package com.dogdailylog.pethotel.bo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.pethotel.PetHotelAPI;
import com.dogdailylog.pethotel.dao.PetHotelMapper;
import com.dogdailylog.pethotel.model.PetHotel;

@Service
public class PetHotelBO {
	
	@Autowired
	private PetHotelMapper petHotelMapper;
	
	public PetHotel getPetHotelById(int id) {
		
		return petHotelMapper.selectPetHotelById(id);
	}

	// 최초 한번만 실행함
	public int addPetHotel() throws ParseException {
		PetHotelAPI api = new PetHotelAPI();
		int rowCnt = 0;
		try {
			String list = api.petHotelList();
			
			JSONParser parser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) parser.parse(list);
	        JSONArray jsonArray = (JSONArray) parser.parse(jsonObject.get("data").toString());
	        
	        for(Object hotel : jsonArray) {
	        	String hotelStr[] = hotel.toString().split(",");
	        	
	        	String name = hotelStr[0].split(":")[1].toString().replaceAll("\\\"","");
	        	String tel = hotelStr[2].split(":")[1].toString().replaceAll("\\\"","");
	        	String address = hotelStr[4].split(":")[1].toString().replaceAll("\\\"","");
	        	
	        	Map<String, Object> map = new HashMap<>();
	        	
	        	map.put("name", name);
	        	map.put("tel", tel);
	        	map.put("address", address);
	        	
	        	//logger.info("%%%%%%%%%%%% hotel %%%%%%%%%%%%% {}", map);
	        	rowCnt += petHotelMapper.insertPetHotel(name, tel, address);
	        	
	        }

		} catch (IOException e) {
			e.printStackTrace();
		}
		return rowCnt;
	}

	public List<PetHotel> getPetHotelList() {
		
		return petHotelMapper.selectPetHotelList();
		
	}

}
