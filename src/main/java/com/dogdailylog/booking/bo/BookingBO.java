package com.dogdailylog.booking.bo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.booking.dao.BookingMapper;

@Service
public class BookingBO {
	@Autowired
	private BookingMapper bookingMapper;
	
	public int addBooking(int userId, int schoolId, Date bookedAt) {
		
		return bookingMapper.insertBooking(userId, schoolId, bookedAt);
	}
	
}
