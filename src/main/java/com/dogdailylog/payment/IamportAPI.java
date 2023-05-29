package com.dogdailylog.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class IamportAPI {
	
	@Value("${iamport.api}")
	private String api;
	
	@Value("${iamport.api-secret}")
	private String apiSecret;
}
