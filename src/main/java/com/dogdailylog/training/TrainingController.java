package com.dogdailylog.training;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dogdailylog.training.bo.TrainingBO;
import com.dogdailylog.training.model.TrainingType;

@Controller
@RequestMapping("/training")
public class TrainingController {
	
	@Autowired
	private TrainingBO trainingBO;
	
	@GetMapping("/my_page_view") // userId 로 받아오는게 나을까?
	public String myPageView(Model model, HttpSession session) {
		
		// 로그인한 유저의 훈련타입 목록 가져오기
		int userId = (int)session.getAttribute("userId");
		
		List<TrainingType> trainingTypeList = new ArrayList<>();
		
		trainingTypeList = trainingBO.getTrainingTypeListByUserId(userId);
		
		model.addAttribute("title", "마이페이지입니다.");
		model.addAttribute("view", "user/myPage");
		model.addAttribute("trainingTypeList", trainingTypeList);
		
		return "template/layout";
	}
}
