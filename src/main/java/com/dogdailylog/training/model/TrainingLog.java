package com.dogdailylog.training.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TrainingLog {
	private int id;
	private int userId;
	private int typeId;
	private String title;
	private String problem;
	private String content;
	private String imagePath;
	private int successCheck;
	private Date createdAt;
	private Date updatedAt;

}
