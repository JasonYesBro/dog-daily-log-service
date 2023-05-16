package com.dogdailylog.training.bo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.common.FileManagerService;
import com.dogdailylog.training.dao.TrainingMapper;
import com.dogdailylog.training.model.TrainingLog;
import com.dogdailylog.training.model.TrainingType;

@Service
public class TrainingBO {
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

}
