package com.dogdailylog.training.bo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogdailylog.training.dao.TrainingMapper;
import com.dogdailylog.training.model.TrainingType;

@Service
public class TrainingBO {
	
	@Autowired
	private TrainingMapper trainingMapper;
	
	// 훈련타입 가져오기
	public List<TrainingType> getTrainingTypeListByUserId(int userId) {
		return trainingMapper.selectTrainingTypeListByUserId(userId);
	}

	// 훈련타입 추가
	public int addTrainingType(int userId, int trainingType, String trainingTitle, Date startedAt, Date finishedAt) {
		
		return trainingMapper.insertTrainingType(userId, trainingType, trainingTitle, startedAt, finishedAt);
	}

}
