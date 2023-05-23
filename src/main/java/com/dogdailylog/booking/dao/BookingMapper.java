package com.dogdailylog.booking.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingMapper {

	public int insertBooking(
			@Param("userId") int userId
			, @Param("schoolId") int schoolId
			, @Param("bookedAt") Date bookedAt);

}
