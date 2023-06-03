package com.dogdailylog.payment;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.payment.bo.PaymentBO;
import com.dogdailylog.payment.bo.PaymentServiceBO;
import com.dogdailylog.payment.model.PaymentInfo;
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
	
	@Autowired
	private PaymentServiceBO paymentServiceBO;

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
			@RequestParam("schoolId") int schoolId
			, @RequestParam("pickUpDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date pickUpDate
			, @RequestParam("pickUpTime") String pickUpTime
			, @RequestParam("price") int price
			, HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();

		// PaymentInfo mapper
//		int rowCnt = paymentServiceBO.addBookAndPayTransaction(userId, schoolId, pickUpDate, pickUpTime, price, "카드");
//		int rowCnt = paymentBO.addPayment(userId, "카드");

		// 분기문을 위해 return Payment로 받음
		PaymentInfo paymentInfo = paymentServiceBO.addBookAndPayTransaction(userId, schoolId, pickUpDate, pickUpTime, price, "카드");
		
		if (paymentInfo != null) {
			// ajax로 넘기기
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
		
		Long paymentId = (long) Integer.parseInt(merchant_uid);
		int status = 1;
		
		logger.info("######### merchant_uid :{}", paymentId);
		
		Map<Object, Object> map = new HashMap<>();

		//주문번호, 결제고유번호, 결제상태를 인자로 넘겨준다
//		int rowCnt = paymentBO.updatePayment(paymentId, status);
		PaymentInfo payment = null;
		payment = paymentBO.updatePayment(paymentId, status);
		
		if (payment != null) {
			map.put("cnt", 1);
		}else {
			map.put("cnt", 0);
		}
		
		return map;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> deleteBookAndPayemnt(
			@RequestParam("bookingId") Long bookingId) {
		Map<String, Object> result = new HashMap<>();
		
		int rowCnt = paymentServiceBO.deleteBookAndPayTransaction(bookingId);
		
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "결제가 취소되었습니다.");
		} else {
			result.put("code", 500);
			result.put("error", "결제내역삭제에 실패했습니다.");
		}
		
		return result;
	}
}
