package com.dogdailylog.payment.bo;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.booking.bo.BookingBO;
import com.dogdailylog.payment.model.PaymentInfo;

@Service
public class PaymentServiceBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingBO bookingBO;
	
	@Autowired
	private PaymentBO paymentBO;
	
	@Transactional
	public PaymentInfo addBookAndPayTransaction(Integer userId, int schoolId, Date pickUpDate, String pickUpTime, int price, String payment) {
		
		Long bookingId = 0L;
		
		try {
			bookingId = bookingBO.addBooking(userId, schoolId, pickUpDate, pickUpTime, price);
		} catch (ParseException e) {
			logger.debug("########## 예약insert - ParseException ##########");
		}

		return paymentBO.addPayment(userId, payment, bookingId); 
	}
	
	@Transactional
	public int deleteBookAndPayTransaction(Long bookingId) {
		
		bookingBO.deleteBooking(bookingId);
		paymentBO.deletePayment(bookingId);
		
		return 1;
	}
}
