package com.dogdailylog.payment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.booking.bo.BookingBO;
import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.payment.bo.PaymentBO;
import com.dogdailylog.payment.model.PaymentInfo;
import com.dogdailylog.payment.model.PaymentView;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PaymentBO paymentBO;

	// Iamport
	private IamportClient iamportClient;
	private IamportAPI iamportApi;
	//IamportClient client = new IamportClient("{가입한 아임포트 계정의 API key}", "{가입한 아임포트 계정의 API secret}");
	
	public PaymentRestController(IamportAPI api) {
		this.iamportApi = api;
		String IAMPORT_API = api.getApi();
		String IAMPORT_API_SECRET = api.getApiSecret();
		this.iamportClient = new IamportClient(IAMPORT_API,IAMPORT_API_SECRET);
		logger.info("########## import API 호출 ########## {}, {}",api.getApi(), api.getApiSecret());
	}
	
	@PostMapping("/create")
	public Map<String, Object> createPayment(
			HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();

		// PaymentInfo mapper
		// TODO payment 넘겨줘야함
		int rowCnt = paymentBO.addPayment(userId, "카드");
		
		if (rowCnt > 0) {
			// ajax로 넘기기
			// TODO view생성하여 booking의 정보와 payment의 정보넘겨야 하나?
			result.put("code", 1);
			result.put("paymentView", paymentBO.generatedPaymentView(userId));
			result.put("result", "주문에 성공했습니다.");
		} else {
			result.put("code", 500);
			result.put("error", "주문을 실패했습니다. 다시 시도해주세요.");
		}
		
		return result;
	}

	@PostMapping("/verify/{imp_uid}")
	public IamportResponse<Payment> paymentByImpUid(
			Model model
			, Locale locale
			, HttpSession session
			, @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException{
		
		return iamportClient.paymentByImpUid(imp_uid);
	}
	
	@PostMapping("/succeed")
	public Map<Object, Object> updateStatus(
			@RequestParam("imp_uid") String imp_uid
			, @RequestParam("merchant_uid") String merchant_uid){
		
		int paymentId = Integer.parseInt(merchant_uid);
		int status = 1;
		
		logger.info("######### merchant_uid :{}", paymentId);
		
		Map<Object, Object> map = new HashMap<>();

		//주문번호, 결제고유번호, 결제상태를 인자로 넘겨준다
		int res = paymentBO.updatePayment(paymentId, status);
		if (res > 0) {
			map.put("cnt", 1);
		}else {
			map.put("cnt", 0);
		}
		
		return map;
	}
}
