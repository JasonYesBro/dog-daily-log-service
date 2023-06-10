package com.dogdailylog.youtube;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/youtube")
@Api(value = "/youtube")
public class YoutubeRestController {
	
//	@Autowired
//	private YoutubeAPI youtubeAPI;

	/**
	 * 반려견 훈련영상 API
	 * @param model
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@ApiOperation(value = "유튜브검색 API")
	@GetMapping("/search")
	public Map<String, Object> search(Model model, @RequestParam(value="keyword", required=false) String keyword) throws IOException, ParseException {
		Map<String, Object> result = new HashMap<>();
		
		// 검색어 값 (기본 검색어)
		String search = "반려견 훈련";
		
		// 키워드 선택 시
		if(keyword != null) {
			search = keyword;
		}
		
//        JSONObject jsonObject = youtubeAPI.search(search);
//		
//		result.put("searchResult", jsonObject);
		
		return result;
	}
}
