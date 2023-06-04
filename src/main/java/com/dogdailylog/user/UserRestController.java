package com.dogdailylog.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.config.Encrypt;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/user")
@RestController
@Api(value = "/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 회원가입 API
	 * @param email
	 * @param name
	 * @param puppyName
	 * @param file
	 * @param password
	 * @param adoptionDate
	 * @return
	 */
	@PostMapping("/sign_up")
	@ApiOperation(value = "회원가입 API")
	public Map<String, Object> signUp(
			@RequestParam("email") String email
			, @RequestParam("name") String name
			, @RequestParam("puppyName") String puppyName
			, @RequestParam("file") MultipartFile file
			, @RequestParam("password") String password
			, @RequestParam("adoptionDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date adoptionDate
			) {
		Map<String, Object> result = new HashMap<>();

		// TODO salt 값
		String salt = Encrypt.getSalt();
		
		// 해싱 처리완료된 비밀번호
		String hashedPassword = Encrypt.md5(password, salt);
		
		int rowCnt = 0;
		
		// TODO salt 값 넣어야 함
		rowCnt = userBO.addUser(email, name, puppyName, file, hashedPassword, salt, adoptionDate);
		
		if(rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "회원가입 성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원가입 실패");
		}
		
		return result;
	}
	
	/**
	 * 닉네임 중복확인 API
	 * @param name
	 * @return
	 */
	@PostMapping("is_duplicated_name")
	@ApiOperation(value = "닉네임중복 API")
	public Map<String, Object> isDuplicatedName(@RequestParam("name") String name) {
		Map<String, Object> result = new HashMap<>();
		
		User user = null;
		user = userBO.getUserByName(name);
		
		if(user == null) {
			result.put("code", 1);
			result.put("result", true);
			
		} else {
			result.put("code", 500);
			result.put("result", false);
		}
		
		return result;
	}
	
	/**
	 * 로그인 API
	 * @param email
	 * @param password
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/sign_in")
	@ApiOperation(value = "로그인 API")
	public Map<String, Object> signIn(
			@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpSession session
			, Model model) {
		Map<String, Object> result = new HashMap<>();
		
		// loginEmail 로 유저의 salt 값가져오기
		String salt = userBO.getSaltByLoginEmail(email);
		
		// pwd + 가져온 salt 값 비밀번호 해싱
		String hashedPassword = Encrypt.md5(password, salt);
		
		User user = null;
		user = userBO.getUserByLoginEmailAndPassword(email, hashedPassword);
		
		if (user != null) {
			result.put("code", 1);
			result.put("result", "로그인에 성공하였습니다.");
			
			// 프로필 이미지
//			model.addAttribute("profileImagePath", user.getProfileImagePath());
			
			// 세션에 유저 정보 저장
			session.setAttribute("userId", user.getId());
			session.setAttribute("userEmail", user.getLoginEmail());
			session.setAttribute("userName", user.getName());
			session.setAttribute("puppyName", user.getPuppyName());
			
			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "로그인에 실패하였습니다.");
		}
		
		return result;
	}
	
	/**
	 * 비밀번호 재설정 API
	 * @param email
	 * @param password
	 * @return
	 */
	@PutMapping("/reset_pwd")
	@ApiOperation(value="비밀번호 재설정 API")
	public Map<String, Object> resetPwd(
			@RequestParam("email") String email
			, @RequestParam("password") String password) {
		
		Map<String, Object> result = new HashMap<>();
		
		// email 로 이미 사용자가 있는지 check
		User user = null;
		user = userBO.getUserByLoginEmail(email);
		
		// user null 체크
		if (user == null) {
			result.put("code", 500);
			result.put("errorMessage", "해당 email로 가입된 사용자가 없습니다.");
			return result;
		}
		
		// DB update
		int rowCnt = 0;
		rowCnt = userBO.resetPasswordByUserEmail(email, password);
		
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "비밀번호가 재설정되었습니다.");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "비밀번호 재설정에 실패하였습니다.");
		}
		
		return result;
	}
	
}
