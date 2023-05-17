package com.dogdailylog.training;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@PostMapping("/log/create")
	public Map<String, Object> createTrainingLog(
			@RequestParam("typeId") int typeId
			, @RequestParam("title") String title
			, @RequestParam("successCheck") boolean successCheck
			, @RequestParam(value="problem", required=false) String problem
			, @RequestParam("content") String content
			, @RequestParam("file") MultipartFile file
			, HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		int rowCnt = 0;
		int userId = (int)session.getAttribute("userId");
		String userEmail = (String)session.getAttribute("userEmail");
		
		// DB insert
		rowCnt = trainingBO.addTrainingLog(userEmail, userId, typeId, title, successCheck, problem, content, file);
		
		// 분기문
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "일지 작성이 되었습니다.");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "일지 작성이 되지 않았습니다.");
		}
		
		// return result
		return result;
	}
	
	@PutMapping("/log/update")
	public Map<String, Object> logUpdate(
			@RequestParam("logId") int logId
			, @RequestParam("title") String title
			, @RequestParam("successCheck") boolean successCheck
			, @RequestParam(value="problem", required=false) String problem
			, @RequestParam("content") String content
			, @RequestParam("file") MultipartFile file
			, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		
		int userId = (int)session.getAttribute("userId");
		String userEmail = (String)session.getAttribute("userEmail");
		
		trainingBO.updateLogByLogId(logId, userId, userEmail, title, successCheck, problem, content, file);
		
		result.put("code", 1);
		result.put("result", "성공");
		
		return result;
	}
	
	@DeleteMapping("/log/delete")
	public Map<String, Object> logDelete(
			@RequestParam("logId") int logId
			, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		
		int userId = (int)session.getAttribute("userId");
		int rowCnt = trainingBO.deleteLogByLogIdAndUserId(logId, userId);
		
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "댓글 삭제하는데 실패하였습니다.");
		}
		return result;
	}
}
