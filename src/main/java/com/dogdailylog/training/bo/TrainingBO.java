package com.dogdailylog.training.bo;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.common.FileManagerService;
import com.dogdailylog.training.dao.TrainingMapper;
import com.dogdailylog.training.model.TrainingLog;
import com.dogdailylog.training.model.TrainingType;

@Service
public class TrainingBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileManagerService fileManager;
	
	@Autowired
	private TrainingMapper trainingMapper;
	
	// 훈련타입 가져오기
	public List<TrainingType> getTrainingTypeListByUserId(int userId) {
		
//		List<TrainingType> trainingTypeList = trainingMapper.selectTrainingTypeListByUserId(userId);
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		for(TrainingType type : trainingTypeList) {
//			// String -> date 문
//			type.setStartedAt(sdf.format(type.getStartedAt()));
//		}
		
		return trainingMapper.selectTrainingTypeListByUserId(userId);
	}

	// 훈련타입 추가
	public int addTrainingType(int userId, int trainingType, String trainingTitle, Date startedAt, Date finishedAt) {
		
		return trainingMapper.insertTrainingType(userId, trainingType, trainingTitle, startedAt, finishedAt);
	}

	public TrainingType getTrainingTypeById(int typeId) {
		
		return trainingMapper.selectTrainingTypeById(typeId);
		
	}

	public int addTrainingLog(String email, int userId, int typeId, String title, boolean successCheck, String problem, String content,
			MultipartFile file) {
		// file 처리
		String logImagePath = null;
		if (file != null) {
			// 서버에 이미지 업로드 후 profileImagePath 받아옴
			logImagePath = fileManager.saveFile(email, file);
		}
		
		
		
		return trainingMapper.insertTrainingLog(userId, typeId, title, successCheck, problem, content, logImagePath);
	}

	public List<TrainingLog> getTrainingLogListByUserId(int userId) {
		
		return trainingMapper.selectTrainingLogListByUserId(userId);
	}

	public List<TrainingLog> getTrainingLogListByUserIdAndTypeId(int userId, Integer typeId) {

		return trainingMapper.selectTrainingLogListByUserIdAndTypeId(userId, typeId);
	}

	public TrainingLog getTrainingLogByLogIdAndUserId(int logId, int userId) {
		
		return trainingMapper.selectTrainingLogByLogIdAndUserId(logId, userId);
		
	}

	public void updateLogByLogId(int logId, int userId, String userEmail, String title, boolean successCheck, String problem, String content, MultipartFile file) {
		
		TrainingLog trainingLog = getTrainingLogByLogIdAndUserId(logId, userId); // 실무에서는 select 를 하는 것이 부담이기 때문에 캐시라는 임시 바구니에 담아놓는다. 그것을 BO단계에서 진행하기 때문에 BO의 메서드를 불러옴 or BO에서 많은 가공을 하고 있기때문에 BO의 가공된 값을 사용하기 위해.
		logger.warn("[update log] log is null. logId: {}. userId: {}", logId, userId);
		
		// 가져온post가 없다면 -> null 체크
		if (trainingLog == null) {
			logger.warn("[update log] log is null. logId: {}. userId: {}", logId, userId);
			return; // return type이 void이기 때문에 
		}
		
		// 업로드한 이미지가 있으면 서버 업로드 -> imagePath받아옴.
		// 업로드가 성공한다면 기존 이미지를 제거함 -> 무작정 제거하지 마쇼.
		String imagePath = null;
		
		if (file != null) {
			// 업로드
			imagePath = fileManager.saveFile(userEmail, file);
			
			// 성공여부 체크 후 기존 이미지 제거
			// -- imagePath가 null이 아닐 때 (성공) 그리고 기존 image가 있을 때 -> 기존이미지 삭제
			if (imagePath != null && trainingLog.getImagePath() != null) {

				// 기존 이미지 제거하기 때문에 파라미터 조심
				fileManager.deleteFile(trainingLog.getImagePath());
			}
		}
		trainingMapper.updateLogByLogId(logId, title, successCheck, problem, content, imagePath);
	}

}
