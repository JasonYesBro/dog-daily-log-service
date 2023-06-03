package com.dogdailylog.payment.model;

import java.util.Optional;

import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PaymentView {
	
	private Optional <BookingInfo> bookingInfoOptional;
	
	private Optional <PaymentInfo> paymentInfoOptional;
	
	private User user;
}
