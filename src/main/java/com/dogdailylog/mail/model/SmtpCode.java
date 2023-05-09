package com.dogdailylog.mail.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class SmtpCode {
	private int id;
	private String userEmail;		
	private String verifyCode;			
	private Date createdAt;
	
}
