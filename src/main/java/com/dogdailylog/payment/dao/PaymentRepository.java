package com.dogdailylog.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dogdailylog.payment.model.PaymentInfo;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentInfo, Long> {
	
}
