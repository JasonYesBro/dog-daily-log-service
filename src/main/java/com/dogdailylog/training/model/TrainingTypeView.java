package com.dogdailylog.training.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TrainingTypeView {
	TrainingType type;
	List<TrainingLog> trainingLogList; // 해당 타입에 속해있는 로그들
	int trainingLogCnt;
	int successTrainingCnt; // 성공한 로그의 갯수
}
