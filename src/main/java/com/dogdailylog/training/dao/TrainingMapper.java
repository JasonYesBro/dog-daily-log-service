package com.dogdailylog.training.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.training.model.TrainingType;

@Repository
public interface TrainingMapper {
	public List<TrainingType> selectTrainingTypeList();

	public int insertTrainingType(
			@Param("userId") int userId
			, @Param("trainingType") int trainingType
			, @Param("trainingTitle") String trainingTitle
			, @Param("startedAt") Date startedAt
			, @Param("finishedAt") Date finishedAt);

	public List<TrainingType> selectTrainingTypeListByUserId(int userId);
}
