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

import com.dogdailylog.booking.dao.BookingRepository;
import com.dogdailylog.booking.model.BookingInfo;

@Service
public class BookingBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookingRepository bookingRepository;
	
	private BookingInfo booking = null;
	
	public Long addBooking(Integer userId, int schoolId, Date pickUpDate, String pickUpTime, int price) throws ParseException {
		
	    // 문자열 -> Date
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        LocalDate bookedAt = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        logger.info("########## bookedAt ########## : {}", bookedAt);

		// JPA로 바꾸면서 builder 활용
		booking = bookingRepository.save(BookingInfo.builder()
							.userId(userId)
							.schoolId(schoolId)
							.pickUpDate(pickUpDate)
							.pickUpTime(pickUpTime)
							.price(price)
							.bookedAt(bookedAt).build());
		
		logger.info("@@@@@@@@@@@@@@ id @@@@@@@@@@@@@@ : {}", booking.getId());

		return booking.getId();
	}
	
	public Optional<BookingInfo> getBookingByUserId(Integer userId) {

		return bookingRepository.findByUserIdAndId(userId, booking.getId());
	}
	
	public void deleteBooking(Long bookingId) {
		// JPA로 변환
		Optional<BookingInfo> bookingOptional = bookingRepository.findById(bookingId);
		bookingOptional.ifPresent(booking -> bookingRepository.delete(booking));
	}

}
