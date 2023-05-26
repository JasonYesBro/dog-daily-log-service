package com.dogdailylog.pethotel.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class PetHotel {
	private int id;
	private String preSchoolName;	
	private String location;
	private Date createdAt;
	private Date updatedAt;

}
