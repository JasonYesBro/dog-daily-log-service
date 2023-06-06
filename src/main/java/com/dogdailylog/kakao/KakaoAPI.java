package com.dogdailylog.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class KakaoAPI {
	
	@Value("${kakao.api-secret}")
	private String apiSecret;
}
