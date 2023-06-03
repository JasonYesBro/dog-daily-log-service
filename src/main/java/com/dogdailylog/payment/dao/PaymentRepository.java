package com.dogdailylog.payment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.payment.model.PaymentInfo;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentInfo, Long> {

	Optional<PaymentInfo> findByBookingId(Long bookingId);

	@Query(value="select * from payment_info where bookingId = :bookingId", nativeQuery=true)
	Optional<PaymentInfo> getLastPaymentInfoByBookingId(@Param("bookingId")Long bookingId);

}
