package com.dogdailylog.booking.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.booking.dao.BookingMapper;
import com.dogdailylog.booking.model.BookingInfo;

@Service
public class BookingBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingMapper bookingMapper;
	
	private BookingInfo booking = null;
	
	public int addBooking(int userId, int schoolId, Date pickUpDate, String pickUpTime, int price) throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//	    Date parsedDate = dateFormat.parse(pickUpTime);
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String todayStr = sdf.format(today.getTime());
		
		Date bookedAt = sdf.parse(todayStr);
	    
		// TODO 빌더로 구현가능?
		booking = new BookingInfo();
		booking.setUserId(userId);
		booking.setSchoolId(schoolId);
		booking.setPickUpDate(pickUpDate);
		booking.setPickUpTime(pickUpTime);
		booking.setPrice(price);
		booking.setBookedAt(bookedAt);
		
		int rowCnt = bookingMapper.insertBooking(booking);
		// int rowCnt = bookingMapper.insertBooking(userId, schoolId, pickUpDate, pickUpTime, bookedAt, price);
		logger.info("@@@@@@@@@@@@@@ id @@@@@@@@@@@@@@ : {}", booking.getId());
		return rowCnt;
	}
	
	public BookingInfo getBookingByUserId(int userId) {
		logger.info("@@@@@@@@@@@@@@ id @@@@@@@@@@@@@@ : {}", booking.getId());
		return bookingMapper.selectBookingByUserIdAndId(userId, booking.getId());
	}
	
	public void deleteBooking(int bookingId) {
		bookingMapper.deleteBookingById(bookingId);
	}
	
}
