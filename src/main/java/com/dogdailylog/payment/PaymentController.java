package com.dogdailylog.payment;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

//@RestController
//@RequestMapping("/payment")
public class PaymentController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// Iamport
	private IamportClient iamportClient;
	private IamportAPI iamportApi;
	//IamportClient client = new IamportClient("{가입한 아임포트 계정의 API key}", "{가입한 아임포트 계정의 API secret}");
	
	public PaymentController(IamportAPI api) {
		this.iamportApi = api;
		String IAMPORT_API = api.getApi();
		String IAMPORT_API_SECRET = api.getApiSecret();
		this.iamportClient = new IamportClient(IAMPORT_API,IAMPORT_API_SECRET);
		logger.info("########## import API 호출 ##########");
	}

	@PostMapping("/verify/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(
			Model model
			, Locale locale
			, HttpSession session
			, @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException{
		
		return iamportClient.paymentByImpUid(imp_uid);
	}
}
