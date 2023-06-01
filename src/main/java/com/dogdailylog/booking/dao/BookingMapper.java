package com.dogdailylog.booking.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.booking.model.BookingInfo;

@Repository
public interface BookingMapper {

//	public int insertBooking(
//			@Param("userId") int userId
//			, @Param("schoolId") int schoolId
//			, @Param("pickUpDate") Date pickUpDate
//			, @Param("pickUpTime") String pickUpTime
//			, @Param("bookedAt") Date bookedAt
//			, @Param("price") int price);
	public int insertBooking(
			BookingInfo bookingInfo);

	public BookingInfo selectBookingByUserIdAndId(
			@Param("userId") int userId
			, @Param("id") Long id);

	public int deleteBookingById(Long id);

}

