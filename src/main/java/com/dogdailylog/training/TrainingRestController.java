package com.dogdailylog.training;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.training.bo.TrainingBO;

@RestController
@RequestMapping("/training")
public class TrainingRestController {

	@Autowired
	private TrainingBO trainingBO;
	
	@PostMapping("/type/create")
	public Map<String, Object> createTrainingType(
			@RequestParam("trainingType") int trainingType
			, @RequestParam("trainingTitle") String trainingTitle
			, @RequestParam("startedAt") @DateTimeFormat(pattern="yyyy-MM-dd") Date startedAt
			, @RequestParam("finishedAt") @DateTimeFormat(pattern="yyyy-MM-dd") Date finishedAt
			, HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		int rowCnt = 0;
		
		// 로그인한 유저의 정보를 불러옴(타입을 추가한 유저)
		int userId = (int)session.getAttribute("userId");
		
		// DB insert
		rowCnt = trainingBO.addTrainingType(userId, trainingType, trainingTitle, startedAt, finishedAt);
		
		// response
		if(rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "훈련타입이 추가되었습니다.");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "훈련타입 추가가 실패했습니다.");
		}
		
		return result;
	}
}
