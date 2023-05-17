package com.dogdailylog.training.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.training.model.TrainingLog;
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

	public TrainingType selectTrainingTypeById(int typeId);

	public int insertTrainingLog(
			@Param("userId") int userId
			, @Param("typeId") int typeId
			, @Param("title") String title
			, @Param("successCheck") boolean successCheck
			, @Param("problem") String problem
			, @Param("content") String content
			, @Param("logImagePath") String logImagePath);

	public List<TrainingLog> selectTrainingLogListByUserId(int userId);

	public List<TrainingLog> selectTrainingLogListByUserIdAndTypeId(
			@Param("userId") int userId
			, @Param("typeId") Integer typeId);

	public TrainingLog selectTrainingLogByLogIdAndUserId(
			@Param("logId") int logId
			, @Param("userId") int userId);

	public void updateLogByLogId(
			@Param("logId") int logId
			, @Param("title") String title
			, @Param("successCheck") boolean successCheck
			, @Param("problem") String problem
			, @Param("content") String content
			, @Param("imagePath") String imagePath);

	public int deleteLogByLogIdAndUserId(
			@Param("logId") int logId
			, @Param("userId") int userId);

	public int updateTrainingTypeByFinishedAt(Date finishedAt);
	

}
