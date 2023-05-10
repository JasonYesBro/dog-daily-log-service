package com.dogdailylog.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/sign_up_view")
	public String signUpView(Model model) {
		
		model.addAttribute("title", "회원가입 페이지입니다.");
		model.addAttribute("view", "user/signUp");
		return "template/layout";
	}
	
	/**
	 * 비밀번호 재설정 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/reset_pwd_view")
	public String resetPwdView(Model model) {
		model.addAttribute("title", "비밀번호 재설정 페이지입니다.");
		model.addAttribute("view", "user/resetPwd");
		
		return "template/layout";
	}
	
	/**
	 * 로그인 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/sign_in_view")
	public String signInView(Model model) {
		model.addAttribute("title", "로그인 페이지입니다.");
		model.addAttribute("view", "user/signIn");
		
		return "template/layout";
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@RequestMapping("/sign_out")
	public String signOut(HttpSession session) {

		// 세션에 있는 모든 것을 비움
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		session.removeAttribute("userEmail");
		
		// 로그인 화면으로 이동
		return "redirect:/user/sign_in_view";
	}
}
