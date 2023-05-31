package com.dogdailylog.map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
public class MapRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DistanceMatrixAPI distanceMatrixAPI;
	
	@RequestMapping("/distance")
	public JSONObject hotelDistance(
			Model model
			, @RequestParam("originLat") String originLat
			, @RequestParam("originLng") String originLng
			, @RequestParam("destinationLat") String destinationLat
			, @RequestParam("destinationLng") String destinationLng) throws ParseException {
		
		JSONObject distanceObj = distanceMatrixAPI.map(originLat, originLng, destinationLat, destinationLng);
		return distanceObj;
	}
}
