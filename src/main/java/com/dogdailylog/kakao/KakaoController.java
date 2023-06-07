package com.dogdailylog.kakao;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dogdailylog.kakao.bo.KakaoLoginBO;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

@Controller
@RequestMapping("/kakao")
public class KakaoController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private KakaoLoginBO kakaoLoginBO;
	
	@GetMapping("/oauth")
	public String kakaoConnect() {
		String url = kakaoLoginBO.connect();
		
		return "redirect:" + url;
	}
	
	@RequestMapping("/callback")
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session, Model model) throws IOException {
		
		// get token
		String accessToken = kakaoLoginBO.getKakaoAccessToken(code);;
		session.setAttribute("accessToken", accessToken);
		
		// get kakao userInfo
		JSONObject response = kakaoLoginBO.getKakaoUserInfo(accessToken);
		
		Map<String, Object> map = (Map<String, Object>) response.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) map.get("profile");
		
		// get nickname과 email
		String name = profile.get("nickname").toString();
		String email = map.get("email").toString();
		
		User user = null;
		user = userBO.getUserByLoginEmail(email);
		
		// 이미 회원가입된 email이라면 session set 후 메인으로
		if(user != null) {
			logger.info("*********** KakaoSignIn success ************");
			
			model.addAttribute("view", "include/main");
			session.setAttribute("userId", user.getId());
			session.setAttribute("userEmail", user.getLoginEmail());
			session.setAttribute("userName", user.getName());
			session.setAttribute("puppyName", user.getPuppyName());
			
			return "template/layout";
		}
		
		// 카카오 전용 회원가입 페이지로
		model.addAttribute("view", "user/kakaoSignUp");
		model.addAttribute("userEmail", email);
		model.addAttribute("userName", name);
		return "template/layout";
	}
	
}
