package com.dogdailylog.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dogdailylog.training.bo.TrainingBO;

@Controller
public class TestController {
	
//	@Autowired
//	private TrainingBO postBO;
	
	@ResponseBody
	@RequestMapping("/test1")
	public String helloWorld() {
		return "test project";
	}
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		
		return map;
	}
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
//	@ResponseBody
//	@RequestMapping("/test4")
//	public List<Map<String, Object>> test4() {
//		
//		List<Map<String, Object>> list = new ArrayList<>();
//		
//		Map<String, Object> result = new HashMap<>();
//		result.put("list",postBO.getPostList()); 
//		
//		list.add(result);
//		
//		return list;
//	}
}
