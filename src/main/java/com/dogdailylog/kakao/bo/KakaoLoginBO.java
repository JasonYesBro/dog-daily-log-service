package com.dogdailylog.kakao.bo;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dogdailylog.kakao.KakaoAPI;
import com.google.common.net.HttpHeaders;

@Service
public class KakaoLoginBO {
	
	@Autowired
	private KakaoAPI kakaoAPI;

	String callbackUrl = "http://52.78.85.116:8080/kakao/callback";
	String KAKAO_API_SECRET = "";
	
	public KakaoLoginBO(KakaoAPI api) {
		this.kakaoAPI = api;
		KAKAO_API_SECRET = kakaoAPI.getApiSecret();
	}
	
	public String connect() {
		
		StringBuffer url = new StringBuffer();
		
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("client_id="+KAKAO_API_SECRET);
		url.append("&redirect_uri="+callbackUrl); // url 이 아닌 Uri였음,.
		url.append("&response_type=code");
		url.append("&scope=account_email");
		
		return url.toString();
	}
	
	public String getKakaoAccessToken(String code) {
		// baseUrl 설정 잘못함 oauth/token 임
		WebClient webClient = WebClient.builder().baseUrl("https://kauth.kakao.com/oauth/token").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		
		JSONObject response = webClient.post()
        .uri(uriBuilder -> uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", KAKAO_API_SECRET)
                .queryParam("redirect_uri", callbackUrl)
                .queryParam("code", code)
                .build())
        .retrieve()
        .bodyToMono(JSONObject.class)
        .block();
//		logger.info("##### token : {} ###", response.get("access_token").toString());
		
		return response.get("access_token").toString();
	}
	
	public JSONObject getKakaoUserInfo(String accessToken) {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kapi.kakao.com")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		
		JSONObject response = webClient.post()
				.uri(uriBuilder -> uriBuilder
						.path("/v2/user/me")
						.build())
				.header("Authorization", "Bearer " + accessToken) // Bearer 공백..있어야함
				.retrieve()
				.bodyToMono(JSONObject.class)
				.block();
		
//		logger.info("##### response : {} ###", response);
		
		return response;
	}
}
