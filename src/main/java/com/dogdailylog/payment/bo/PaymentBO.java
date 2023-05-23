package com.dogdailylog.payment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.payment.dao.PaymentMapper;

@Service
public class PaymentBO {
	@Autowired
	private PaymentMapper paymentMapper;
	
	public int addPayment(int bookingId, int userId, int price, String payment, String approval) {
		
		return paymentMapper.insertPayment(bookingId, userId, price, payment, approval);
	}
}
