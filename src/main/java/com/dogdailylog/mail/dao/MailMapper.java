package com.dogdailylog.mail.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.mail.model.SmtpCode;

@Repository
public interface MailMapper {

	public int insertSmtpCodeByEmail(
			@Param("verifyCode") String verifyCode
			, @Param("email") String email);

	public SmtpCode selectSmtpCodeByVerifyCode(String verifyCode);
}
