package com.dogdailylog.booking.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class BookingInfo {
	private int id;
	private int userId;
	private int schoolId;
	private Date pickUpDate;
	private String pickUpTime;
	private Date bookedAt;
	private int price;
	private int status;
	private Date createdAt;
	private Date updatedAt;

}
