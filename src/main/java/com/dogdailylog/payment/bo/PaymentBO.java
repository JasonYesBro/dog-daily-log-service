package com.dogdailylog.payment.bo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.booking.dao.BookingRepository;
import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.payment.dao.PaymentMapper;
import com.dogdailylog.payment.dao.PaymentRepository;
import com.dogdailylog.payment.model.PaymentInfo;
import com.dogdailylog.payment.model.PaymentView;
import com.dogdailylog.user.bo.UserBO;
import com.dogdailylog.user.model.User;

@Service
public class PaymentBO {
	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private UserBO userBO;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private Optional <BookingInfo> bookingInfoOptional = null;
	private Optional <PaymentInfo> paymentInfoOptional = null;
	int price = 100;
	
	@Transactional
	public PaymentInfo addPayment(Integer userId, String payment, Long bookingId) {

		bookingInfoOptional = bookingRepository.findByUserIdAndId(userId, bookingId);
		
		// 단일상품 결제
		bookingInfoOptional.ifPresent( b -> price = bookingInfoOptional.get().getPrice() );
		
		PaymentInfo paymentInfo = null;
		
		paymentInfo = paymentRepository.save(PaymentInfo.builder()
					.bookingId(bookingId)
					.userId(userId)
					.price(price)
					.payment(payment)
					.approval(0)
					.build());
		
		return paymentInfo;
	}

	public PaymentInfo getLastPaymentInfoByBookingId(Long id) {

		return paymentMapper.selectLastPaymentByBookingId(id);
	}

	public PaymentInfo updatePayment(Long id, int status) {
		PaymentInfo paymentInfo = paymentRepository.findById(id).orElse(null);
		paymentInfo = paymentInfo.toBuilder() // 기존 내용은 그대로
                .approval(1)
                .updatedAt(LocalDateTime.now())
                .build();
		
		return paymentRepository.save(paymentInfo);
	}
	
	public void deletePayment(Long bookingId) {
		Optional<PaymentInfo> paymentInfoOptional = paymentRepository.findByBookingId(bookingId);
		paymentInfoOptional.ifPresent(
				p -> paymentRepository.delete(p)
				);
	}
	
	
	public PaymentView generatedPaymentView(Integer userId) {
		PaymentView paymentView = new PaymentView();
		
		User user = userBO.getUserById(userId);
		
		// Optional 타입을 사용하여 booking이 있으면 payment를 가져온다.
		// lambda안에서 로컬변수의 값을 바꾸지 못하므로 클래스변수를 선언함.
		bookingInfoOptional.ifPresent(
				b -> paymentInfoOptional = paymentRepository.findLastPaymentInfoByBookingId(b.getId())
				);
		
		paymentView.setBookingInfoOptional(bookingInfoOptional);
		paymentView.setPaymentInfoOptional(paymentInfoOptional);
		paymentView.setUser(user);
		
		return paymentView;
	}
}
