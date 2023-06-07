package com.dogdailylog.payment.bo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dogdailylog.booking.bo.BookingBO;
import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.payment.model.PaymentInfo;

@SpringBootTest
class PaymentServiceBOTest {
	@Autowired
	BookingBO bookingBO;
	
	@Autowired
	PaymentBO paymentBO;
	
	@Autowired
	PaymentServiceBO paymentServiceBO;
	
	@Transactional
	@Test
	void 예약및결제하기() throws ParseException {
		
		Long bookingId = 0L;
		
		BookingInfo bookingInfo = new BookingInfo();
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		today = sdf.parse("2023-06-01");
		
		
		bookingInfo = BookingInfo.builder()
			.userId(6)
			.schoolId(25)
			.pickUpDate(today)
			.pickUpTime("15:00")
			.price(100)
			.build();
		
		bookingId = bookingBO.addBooking(bookingInfo.getUserId(), bookingInfo.getSchoolId(), bookingInfo.getPickUpDate(), bookingInfo.getPickUpTime(), bookingInfo.getPrice());
		
		PaymentInfo paymentInfo = new PaymentInfo();
		
		paymentInfo = paymentBO.addPayment(bookingInfo.getUserId(), "카드", bookingId); 
		
		assertNotNull(paymentInfo);
	}
	
	@Transactional
	@Test
	void 예약및결제취소하기() throws ParseException {
		
		Long bookingId = 0L;
		
		BookingInfo bookingInfo = new BookingInfo();
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		today = sdf.parse("2023-06-01");
		
		bookingInfo = BookingInfo.builder()
			.userId(6)
			.schoolId(25)
			.pickUpDate(today)
			.pickUpTime("15:00")
			.price(100)
			.build();
		
		bookingId = bookingBO.addBooking(bookingInfo.getUserId(), bookingInfo.getSchoolId(), bookingInfo.getPickUpDate(), bookingInfo.getPickUpTime(), bookingInfo.getPrice());
		
		PaymentInfo paymentInfo = new PaymentInfo();
		
		paymentInfo = paymentBO.addPayment(bookingInfo.getUserId(), "카드", bookingId); 
		
		
		int result = paymentServiceBO.deleteBookAndPayTransaction(bookingId);
		
		assertEquals(1, result);
		assertNotNull(paymentInfo);
	}

}
