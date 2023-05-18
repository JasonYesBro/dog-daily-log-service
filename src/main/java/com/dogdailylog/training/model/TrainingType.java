package com.dogdailylog.training.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Setter
public class TrainingType {
	private int id;
	private int userId;
	private int trainingType;			
	private String trainingTitle;
	private Date startedAt;
	private Date finishedAt;
	private int status;
	private Date createdAt;
	private Date updatedAt;
}
