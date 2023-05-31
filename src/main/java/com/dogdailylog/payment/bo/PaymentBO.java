package com.dogdailylog.payment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.booking.bo.BookingBO;
import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.payment.dao.PaymentMapper;
import com.dogdailylog.payment.model.PaymentInfo;
import com.dogdailylog.payment.model.PaymentView;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

@Service
public class PaymentBO {
	@Autowired
	private PaymentMapper paymentMapper;
	
	@Autowired
	private BookingBO bookingBO;
	
	@Autowired
	private UserBO userBO;
	
	@Transactional
	public int addPayment(int userId, String payment) {
		
		BookingInfo bookinInfo = bookingBO.getBookingByUserId(userId);
		
		int price = 100;
		
		// TODO
		int bookingId;
		
		// 단일상품 결제
		price = bookinInfo.getPrice();
		bookingId = bookinInfo.getId();
		
		return paymentMapper.insertPayment(bookingId, userId, price, payment);
	}

	public PaymentInfo getLastPaymentInfoByBookingId(int id) {

		return paymentMapper.selectLastPaymentByBookingId(id);
	}

	public int updatePayment(int id, int status) {
		// bookingMapper. // booking의 status를 업데이트 해줘야함
		return paymentMapper.updatePaymentById(id, status);
	}
	
	public int deletePayment(int bookingId) {
		return paymentMapper.deletePaymentByBookingId(bookingId);
	}
	
	public PaymentView generatedPaymentView(int userId) {
		PaymentView paymentView = new PaymentView();
		
		User user = userBO.getUserById(userId);
		// get booking by userId
		BookingInfo booking = bookingBO.getBookingByUserId(userId);
		PaymentInfo paymentInfo = getLastPaymentInfoByBookingId(booking.getId());
		
		paymentView.setBookingInfo(booking);
		paymentView.setPaymentInfo(paymentInfo);
		paymentView.setUser(user);
		
		return paymentView;
	}
}
