package com.dogdailylog.booking.bo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.booking.dao.BookingMapper;
import com.dogdailylog.booking.dao.BookingRepository;
import com.dogdailylog.booking.model.BookingInfo;

@Service
public class BookingBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingMapper bookingMapper;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	private BookingInfo booking = null;
	
	public Long addBooking(int userId, int schoolId, Date pickUpDate, String pickUpTime, int price) throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//	    Date parsedDate = dateFormat.parse(pickUpTime);
		
//		Date today = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		String todayStr = sdf.format(today.getTime());
//		
//		Date bookedAt = sdf.parse(todayStr);
		
		 
	        // 문자열 -> Date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        LocalDate bookedAt = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        logger.info("########## bookedAt ########## : {}", bookedAt);
		// TODO 빌더로 구현가능? 
//		booking = new BookingInfo();
//		booking.setUserId(userId);
//		booking.setSchoolId(schoolId);
//		booking.setPickUpDate(pickUpDate);
//		booking.setPickUpTime(pickUpTime);
//		booking.setPrice(price);
//		booking.setBookedAt(bookedAt);
		
		// JPA로 바꾸면서 builder 활용
		booking = bookingRepository.save(BookingInfo.builder()
							.userId(userId)
							.schoolId(schoolId)
							.pickUpDate(pickUpDate)
							.pickUpTime(pickUpTime)
							.price(price)
							.bookedAt(bookedAt).build());
		
//		int rowCnt = bookingMapper.insertBooking(booking);
		// int rowCnt = bookingMapper.insertBooking(userId, schoolId, pickUpDate, pickUpTime, bookedAt, price);
		logger.info("@@@@@@@@@@@@@@ id @@@@@@@@@@@@@@ : {}", booking.getId());
//		return rowCnt;
		return booking.getId();
	}
	
	public Optional<BookingInfo> getBookingByUserId(int userId) {
		logger.info("@@@@@@@@@@@@@@ id @@@@@@@@@@@@@@ : {}", booking.getId());
//		return bookingMapper.selectBookingByUserIdAndId(userId, booking.getId());

		//JPA 네이티브쿼리를 이용
		return bookingRepository.findByUserIdAndId(userId, booking.getId());
	}
	
	public void deleteBooking(Long bookingId) {
//		bookingMapper.deleteBookingById(bookingId);
		
		// JPA로 변환
		Optional<BookingInfo> bookingOptional = bookingRepository.findById(bookingId);
		bookingOptional.ifPresent(booking -> bookingRepository.delete(booking));
	}
	
	
	
}
