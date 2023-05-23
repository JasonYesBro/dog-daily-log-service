package com.dogdailylog.payment.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMapper {
	public int insertPayment(
			@Param("bookingId") int bookingId
			, @Param("userId") int userId
			, @Param("price") int price
			, @Param("payment") String payment
			, @Param("approval") String approval);
}
