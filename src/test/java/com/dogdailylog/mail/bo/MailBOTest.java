package com.dogdailylog.mail.bo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import com.dogdailylog.mail.model.SmtpCode;

@SpringBootTest
class MailBOTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MailBO mailBO;

	//@Test
	void 인증코드생성() {
		logger.info("@@@@@@@@@@@ 인증코드 생성 테스트 @@@@@@@@@@@@");
		
		String key = "";
		key = mailBO.createKey();
		
		assertNotNull(key);
	}
	
	@Test
	void 인증하기() {
		logger.info("@@@@@@@@@@@ 인증코드 인증하기 테스트 @@@@@@@@@@@@");
		
		String testEmail = "tmdgus5611@gmail.com";
		
		SmtpCode smtpCode = new SmtpCode();
		
		String code = "";
		code = mailBO.createKey(); // return boolean
		
		mailBO.send(testEmail);
		
		smtpCode.setUserEmail(testEmail);
		smtpCode.setVerifyCode(code);
		
		mailBO.setSmtpCodeByEmail(testEmail);
		
		SmtpCode testSmtpCode = new SmtpCode();
		testSmtpCode = mailBO.getSmtpCodeByVerifyCode(smtpCode.getVerifyCode());
		
		if (ObjectUtils.isEmpty(smtpCode)) {
			logger.info("@@@@@@@@@@ smtpCode is null @@@@@@@@@@");
			return;
		}
		
		assertThat(smtpCode.getVerifyCode()).isEqualTo(testSmtpCode.getVerifyCode());
		
	}

}
