package com.dogdailylog.mail;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.mail.bo.MailBO;
import com.dogdailylog.mail.model.SmtpCode;
import com.dogdailylog.user.bo.UserBO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "/api")
public class MailRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MailBO mailBO;
	
	@Autowired
	private UserBO userBO;
	
	@ApiOperation(value = "인증코드 메일보내기 API")
	@PostMapping("/mail")
	public Map<String, Object> verifyCodeMail(
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		if(userBO.getUserByLoginEmail(email) != null) {
			result.put("code", 500);
			result.put("result", false);
			result.put("errorMessage", "이미 사용중인 이메일입니다.");
			return result;
		}
		
		// 인증코드 생성
		String code = mailBO.createKey();
		
		if(!code.equals(null)) {
			mailBO.send(email);
			
			// email - verifyCode DB insert
			mailBO.setSmtpCodeByEmail(email);
			
			result.put("code", 1);
			result.put("result", true);
			
		} else {
			result.put("code", 500);
			result.put("result", false);
			result.put("errorMessage", "인증코드 메일을 보내는데 실패했습니다.");
		}
		
		return result;
	}
	
	// 임시코드
	@ApiOperation(value = "비밀번호 재설정 중 인증코드 메일보내기 API")
	@PostMapping("/resetmail")
	public Map<String, Object> verifyCodeMailInReset(
			@RequestParam("email") String email) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 인증코드 생성
		String code = mailBO.createKey();
		
		if(!code.equals(null)) {
			mailBO.send(email);
			
			// email - verifyCode DB insert
			mailBO.setSmtpCodeByEmail(email);
			
			result.put("code", 1);
			result.put("result", true);
			
		} else {
			result.put("code", 500);
			result.put("result", false);
			result.put("errorMessage", "인증코드 메일을 보내는데 실패했습니다.");
		}
		
		return result;
	}
	
	@ApiOperation(value = "인증하기 API")
	@PostMapping("/verify")
	public Map<String, Object> checkVerifyCode(
			@RequestParam("verifyCode") String verifyCode) {
		Map<String, Object> result = new HashMap<>();

		SmtpCode smtpCode = null;
		smtpCode = mailBO.getSmtpCodeByVerifyCode(verifyCode);
		
		if ( smtpCode != null ) {
			result.put("code", 1);
			result.put("result", "인증에 성공하였습니다.");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "인증에 실패하였습니다.");
		}
		
		return result;
	}
	
}
