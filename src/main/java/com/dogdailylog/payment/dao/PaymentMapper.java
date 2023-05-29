package com.dogdailylog.payment.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.payment.model.PaymentInfo;

@Repository
public interface PaymentMapper {
	public int insertPayment(
			@Param("bookingId") int bookingId
			, @Param("userId") int userId
			, @Param("price") int price
			, @Param("payment") String payment);

	public PaymentInfo selectLastPaymentByBookingId(int id);
	
	public int updatePaymentById(
			@Param("id") int id
			, @Param("approval") int approval);
}
