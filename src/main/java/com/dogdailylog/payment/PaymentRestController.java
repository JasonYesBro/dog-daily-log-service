package com.dogdailylog.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.payment.bo.PaymentBO;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {
	
	@Autowired
	private PaymentBO paymentBO;
	
	@PostMapping("/create")
	public Map<String, Object> createPayment() {
		
		Map<String, Object> result = new HashMap<>();
		
		// paymentBO.addPayment(0, 0, 0, null, null);
		
		return result;
	}
}
