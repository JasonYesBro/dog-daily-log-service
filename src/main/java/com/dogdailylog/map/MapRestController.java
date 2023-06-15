package com.dogdailylog.map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/map")
@Api(tags="지도 API")
public class MapRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DistanceMatrixAPI distanceMatrixAPI;
	
	@ApiOperation(value="거리계산 API")
	@PostMapping("/distance")
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
