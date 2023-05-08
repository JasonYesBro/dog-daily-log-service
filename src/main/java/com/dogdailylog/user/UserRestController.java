package com.dogdailylog.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.config.Encrypt;
import com.dogdailylog.user.bo.UserBO;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("email") String email
			, @RequestParam("name") String name
			, @RequestParam("puppyName") String puppyName
			, @RequestParam("file") MultipartFile file
			, @RequestParam("password") String password
			, @RequestParam("adoptionDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date adoptionDate
			) {
		String hashedPassword = Encrypt.md5(password);
		
		Map<String, Object> result = new HashMap<>();
		int rowCnt = 0;
		rowCnt = userBO.addUser(email, name, puppyName, file, hashedPassword, adoptionDate);
		
		if(rowCnt > 0) {
			result.put("data", 1);
			result.put("result", "회원가입 성공");
		} else {
			result.put("data", 500);
			result.put("errorMessage", "회원가입 실패");
		}
		
		return result;
	}
}
