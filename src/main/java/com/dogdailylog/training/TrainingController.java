package com.dogdailylog.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dogdailylog.training.bo.TrainingBO;
import com.dogdailylog.training.model.TrainingLog;
import com.dogdailylog.training.model.TrainingType;
import com.dogdailylog.training.model.TrainingTypeView;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/training")
@ApiIgnore
public class TrainingController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private TrainingBO trainingBO;
	
	@GetMapping("/my_page_view") // userId 로 받아오는게 나을까?
	public String myPageView(Model model, HttpSession session) {
		
		User user = null;
		List<TrainingTypeView> trainingTypeViewList = new ArrayList<>();
		
		// 로그인한 유저의 훈련타입 목록 가져오기
		Integer userId = (Integer)session.getAttribute("userId");
		
		user = userBO.getUserByLoginEmail((String)session.getAttribute("userEmail"));		

		trainingTypeViewList = trainingBO.getTrainingTypeViewListByUserId(userId);

		Map<String, Object> userInfo = new HashMap<>();
		
		userInfo.put("userName", user.getName());
		userInfo.put("puppyName", user.getPuppyName());
		userInfo.put("profileImagePath", user.getProfileImagePath());
		
		model.addAttribute("title", "마이페이지입니다.");
		model.addAttribute("view", "log/myPage");
		model.addAttribute("trainingTypeViewList", trainingTypeViewList);
		model.addAttribute("userInfo", userInfo);
		
		return "template/layout";
	}
	
	@GetMapping("/log_create_view")
	public String logCreateView(Model model, HttpSession session, @RequestParam(value="typeId", required=false) Integer typeId) {
		
		// typeId param이 들어오지 않을 경우
		if (typeId == null) {
			return "redirect:/training/my_page_view";
		}
		
		// 파라미터로 넘겨진 타입이 있는지 확인
		TrainingType trainingType = null;		
		trainingType = trainingBO.getTrainingTypeById(typeId);

		if (trainingType != null) {
			model.addAttribute("title", "훈련일지를 작성해보세요.");
			model.addAttribute("view", "log/logCreate");
			model.addAttribute("trainingType", trainingType);
			
		} else {
			return "redirect:/training/my_page_view";
		}
		
		return "template/layout";
	}
	
	@GetMapping("/calendar_view")
	public String calendarView(Model model, HttpSession session) {
		
		int userId = (int)session.getAttribute("userId");
		
		// 훈련로그 가져오기
		List<TrainingLog> trainingLogList = new ArrayList<>();
		
		trainingLogList = trainingBO.getTrainingLogListByUserId(userId);
		
		model.addAttribute("title", "훈련달력입니다.");
		model.addAttribute("view", "log/calendar");
		model.addAttribute("trainingLogList", trainingLogList);
		
		return "template/layout";
	}
	
	@GetMapping("/log_list_view")
	public String logListView(Model model, HttpSession session, @RequestParam(value="typeId", required=false) Integer typeId) {
		
		int userId = (int)session.getAttribute("userId");
		
		List<TrainingLog> trainingLogList = new ArrayList<>();
		
		trainingLogList = trainingBO.getTrainingLogListByUserIdAndTypeIdLimit(userId, typeId);
		
		model.addAttribute("title", "작성한 훈련일지입니다.");
		model.addAttribute("view", "log/logList");
		model.addAttribute("trainingLogList", trainingLogList);
		
		return "template/layout";
	}
	
	@GetMapping("/log_detail_view/{logId}")
	public String logDetailView(Model model, @PathVariable("logId") int logId, HttpSession session) {
		int userId = (int) session.getAttribute("userId");
		
		TrainingLog trainingLog = null;
		
		trainingLog = trainingBO.getTrainingLogByLogIdAndUserId(logId, userId);
		model.addAttribute("title", trainingLog.getTitle());
		model.addAttribute("view", "log/logDetail");
		model.addAttribute("trainingLog", trainingLog);
		
		return "template/layout";
	}
	
	@GetMapping("/more_list_view")
	public Object moreLigListView(Model model, HttpSession session
			, @RequestParam(value="typeId", required=false) Integer typeId
			, @RequestParam("cnt") int cnt) {
		
		int userId = (int)session.getAttribute("userId");
		
		List<TrainingLog> trainingLogList = new ArrayList<>();
		
		trainingLogList = trainingBO.getTrainingLogListByUserIdAndTypeIdAndCnt(userId, typeId, cnt);
		if(trainingLogList != null) {
//			result.put("code", 500);
			model.addAttribute("trainingLogList", trainingLogList);
			// 조각페이지 반환
			return "log/moreList";
		} else {
//			result.put("code", 1);
			return "log/noneItem";
		}
	}
}
