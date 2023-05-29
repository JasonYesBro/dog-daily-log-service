package com.dogdailylog.payment.model;

import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.user.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class PaymentView {
	
	private BookingInfo bookingInfo;
	
	private PaymentInfo paymentInfo;
	
	private User user;
}
