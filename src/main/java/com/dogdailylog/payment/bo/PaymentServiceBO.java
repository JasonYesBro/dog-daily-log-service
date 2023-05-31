package com.dogdailylog.payment.bo;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.booking.bo.BookingBO;

@Service
public class PaymentServiceBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingBO bookingBO;
	
	@Autowired
	private PaymentBO paymentBO;
	
	@Transactional
	public int addBookAndPayTransaction(int userId, int schoolId, Date pickUpDate, String pickUpTime, int price, String payment) {
		try {
			bookingBO.addBooking(userId, schoolId, pickUpDate, pickUpTime, price);
		} catch (ParseException e) {
			logger.debug("########## 예약insert - ParseException ##########");
		}
		int rowCnt = paymentBO.addPayment(userId, payment);
		return rowCnt;
	}
	
	@Transactional
	public int deleteBookAndPayTransaction(int bookingId) {
		
		bookingBO.deleteBooking(bookingId);
		int rowCnt = paymentBO.deletePayment(bookingId);
		
		return rowCnt;
	}
}
