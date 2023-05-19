package com.dogdailylog.training.bo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	
	private static final int POST_MAX_SIZE = 6;
	
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
		
		return trainingMapper.selectTrainingLogListByUserId(userId, POST_MAX_SIZE);
	}

	public List<TrainingLog> getTrainingLogListByUserIdAndTypeId(int userId, Integer typeId) {
		
		return trainingMapper.selectTrainingLogListByUserIdAndTypeId(userId, typeId, POST_MAX_SIZE);
	}
	
	// cnt 까지 실험
	public List<TrainingLog> getTrainingLogListByUserIdAndTypeIdAndCnt(int userId, Integer typeId, int cnt) {

		int showLogNum = POST_MAX_SIZE * cnt;
		
		return trainingMapper.selectTrainingLogListByUserIdAndTypeIdAndCnt(userId, typeId, showLogNum);
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

	public int deleteLogByLogIdAndUserId(int logId, int userId) {
		
		TrainingLog trainingLog = getTrainingLogByLogIdAndUserId(logId, userId); // 실무에서는 select 를 하는 것이 부담이기 때문에 캐시라는 임시 바구니에 담아놓는다. 그것을 BO단계에서 진행하기 때문에 BO의 메서드를 불러옴 or BO에서 많은 가공을 하고 있기때문에 BO의 가공된 값을 사용하기 위해.
		
		// 가져온post가 없다면 -> null 체크
		if (trainingLog == null) {
			logger.warn("[delete log] log is null. logId: {}. userId: {}", logId, userId);
			return 0;
		}
		
		String imagePath = null;
		// 삭제할 시에 폴더의 이미지 또한 같이 삭제해줘야 함
		imagePath = trainingLog.getImagePath();

		// -- imagePath가 null이 아닐 때 (성공) 그리고 기존 image가 있을 때 -> 기존이미지 삭제
		if (imagePath != null) {
			// 기존 이미지 제거하기 때문에 파라미터 조심
			fileManager.deleteFile(trainingLog.getImagePath());
		}
		
		return trainingMapper.deleteLogByLogIdAndUserId(logId, userId);
	}

//	public int deleteOverduedTypeList() throws ParseException {
//		int rowCnt = 0;
//		
//		//typeList 를 DB에서 조회해옴
//		List<TrainingType> typeList = trainingMapper.selectTrainingTypeList();
//		
//		// finishedAt을 담을 바구니 생성
//		List<Date> finishedAtList = new ArrayList<>();
//		
//		// sdf
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Date today = new Date();
//		
//		String strDate = sdf.format(today);
//
//		today = sdf.parse(strDate);
//		
//		// List<TrainingType> 돌면서 List<Date>에 담음
//		for(TrainingType type : typeList) {
//			finishedAtList.add(type.getFinishedAt());
//		}
//		
//		// 5/13 5/14
//		// 종료일이 오늘날짜 기준으로 지났는지 확인
//		for (Date finishedAt : finishedAtList) {
//			if( today.after( finishedAt ) ) {
//				logger.info("@@@@@@@@@@@@@@@ 종료일이 지났습니다. @@@@@@@@@@@@@@@@@ 종료일 : {}, 오늘 :{}", finishedAt, today);
//				
//				// logic - code 구현
//				rowCnt += trainingMapper.updateTrainingTypeByFinishedAt(finishedAt);
//			}
//		}
//		
//		return rowCnt;
//	}
	
	public int updateOverduedTypeList() throws ParseException {
		
		//typeList 를 DB에서 조회해옴
		List<TrainingType> typeList = trainingMapper.selectTrainingTypeList();
		
		// finishedAt을 담을 바구니 생성
		List<Date> finishedAtList = new ArrayList<>();
		
		// 기간이 지난 날짜만 담음
		List<Date> overduedList = new ArrayList<>();
		
		// sdf
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date today = new Date();
		
		String strDate = sdf.format(today);

		today = sdf.parse(strDate);
		
		// List<TrainingType> 돌면서 List<Date>에 담음
		for(TrainingType type : typeList) {
			finishedAtList.add(type.getFinishedAt());
		}
		
		for (Date finishedAt : finishedAtList) {
			if( today.after( finishedAt ) ) {
				logger.info("@@@@@@@@@@@@@@@ 종료일이 지났습니다. @@@@@@@@@@@@@@@@@ 종료일 : {}, 오늘 :{}", finishedAt, today);
				// logic - code 구현
				overduedList.add(finishedAt);
			}
		}
		
		return trainingMapper.updateTrainingTypeByFinishedAt(overduedList);
	}

}
