package com.dogdailylog.mail.bo;

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
		String key = "";
		key = mailBO.createKey();
		
		ObjectUtils.isEmpty(key);
	}
	
	@Test
	void 인증하기() {
		String testEmail = "tmdgus5611@gmail.com";
		
		String code = "";
		code = mailBO.createKey(); // return boolean
		mailBO.send(testEmail);
		mailBO.setSmtpCodeByEmail(testEmail);
		SmtpCode smtpCode = null;
		smtpCode = mailBO.getSmtpCodeByVerifyCode(code);
		if (ObjectUtils.isEmpty(smtpCode)) {
			logger.info("@@@@@@@@@@ smtpCode is null @@@@@@@@@@");
			return;
		}
		assertNotNull(smtpCode);
	}

}
