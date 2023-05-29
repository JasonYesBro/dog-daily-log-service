package com.dogdailylog.payment.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class PaymentInfo {
	private int id;
	private int bookingId;
	private int userId;
	private int price;
	private String payment;
	private String approval;
	private Date createdAt;
	private Date updatedAt;

}
