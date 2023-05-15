package com.dogdailylog.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dogdailylog.training.bo.TrainingBO;
import com.dogdailylog.training.model.TrainingType;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

@Controller
@RequestMapping("/training")
public class TrainingController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private TrainingBO trainingBO;
	
	@GetMapping("/my_page_view") // userId 로 받아오는게 나을까?
	public String myPageView(Model model, HttpSession session) {
		
		// 로그인한 유저의 훈련타입 목록 가져오기
		Integer userId = (Integer)session.getAttribute("userId");
		
		List<TrainingType> trainingTypeList = new ArrayList<>();
		
		trainingTypeList = trainingBO.getTrainingTypeListByUserId(userId);

		User user = null;
		user = userBO.getUserByLoginEmail((String)session.getAttribute("userEmail"));
		

		Map<String, Object> userInfo = new HashMap<>();
		
		userInfo.put("userName", user.getName());
		userInfo.put("puppyName", user.getPuppyName());
		userInfo.put("profileImagePath", user.getProfileImagePath());
		
		
		model.addAttribute("title", "마이페이지입니다.");
		model.addAttribute("view", "log/myPage");
		model.addAttribute("trainingTypeList", trainingTypeList);
		model.addAttribute("userInfo", userInfo);
		
		return "template/layout";
	}
	
	@GetMapping("/log_create_view")
	public String logCreateView(Model model, HttpSession session, @RequestParam("typeId") int typeId) {
		
		// 파라미터로 넘겨진 타입이 있는지 확인
		TrainingType trainingType = null;		
		trainingType = trainingBO.getTrainingTypeById(typeId);

		if (trainingType != null) {
			model.addAttribute("title", "훈련일지를 작성해보세요.");
			model.addAttribute("view", "log/logCreate");
			model.addAttribute("typeId", typeId);
			
		} else {
			return "redirect:/training/my_page_view";
		}
		
		return "template/layout";
	}
}
