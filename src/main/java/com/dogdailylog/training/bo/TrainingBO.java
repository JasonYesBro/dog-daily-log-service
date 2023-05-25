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
import com.dogdailylog.training.model.TrainingTypeView;

@Service
public class TrainingBO {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int POST_MAX_SIZE = 6;
	
	@Autowired
	private FileManagerService fileManager;
	
	@Autowired
	private TrainingMapper trainingMapper;
	
	// 훈련타입 가져오기
	/**
	 * 훈련타입리스트 가져오기
	 * @param userId
	 * @return
	 */
	public List<TrainingTypeView> getTrainingTypeViewListByUserId(int userId) {
		
		List<TrainingType> trainingTypeList = new ArrayList<>();
		List<TrainingTypeView> trainingTypeViewList = new ArrayList<>();
		
		trainingTypeList = trainingMapper.selectTrainingTypeListByUserId(userId);
		
		for (TrainingType type : trainingTypeList) {

			TrainingTypeView  trainingTypeView = new TrainingTypeView();
			List<TrainingLog> trainingLogList = new ArrayList<>();
			
			// DB에서 해당타입의 로그들을 select
			trainingLogList = getTrainingLogListByUserIdAndTypeId(userId, type.getId());
			int trainingLogCnt = 0;
			int successCnt = 0;
			
			trainingLogCnt = trainingLogList.size();
			
			// 로그들을 돌면서 성공인 것만 추가
			for (TrainingLog log : trainingLogList) {
				int success = 0; 
				
				success = log.getSuccessCheck();
				
				if(success == 1) {
					successCnt += 1;
				}
			}
			
			logger.debug("%%%%%%%%%%% 성공한 훈련 %%%%%%%%%%% 성공갯수 : {}, 전체 갯수 : {}", successCnt, trainingLogCnt);
			
			// view객체에 set
			trainingTypeView.setType(type);
			trainingTypeView.setTrainingLogList(trainingLogList); // 타입별 로그들 저장해야함 how?
			trainingTypeView.setTrainingLogCnt(trainingLogCnt);
			trainingTypeView.setSuccessTrainingCnt(successCnt);
			
			// viewList에 add
			trainingTypeViewList.add(trainingTypeView);
		}
		
		return trainingTypeViewList;
	}

	/**
	 * 훈련타입 추가 API
	 * @param userId
	 * @param trainingType
	 * @param trainingTitle
	 * @param startedAt
	 * @param finishedAt
	 * @return
	 */
	public int addTrainingType(int userId, int trainingType, String trainingTitle, Date startedAt, Date finishedAt) {
		
		return trainingMapper.insertTrainingType(userId, trainingType, trainingTitle, startedAt, finishedAt);
	}

	/**
	 * 훈련타입
	 * @param typeId
	 * @return
	 */
	public TrainingType getTrainingTypeById(int typeId) {
		
		return trainingMapper.selectTrainingTypeById(typeId);
		
	}

	
	/**
	 * 일지추가하기 API
	 * @param email
	 * @param userId
	 * @param typeId
	 * @param title
	 * @param successCheck
	 * @param problem
	 * @param content
	 * @param file
	 * @return
	 */
	public int addTrainingLog(String email, int userId, int typeId, String title, int successCheck, String problem, String content,
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
	
	/**
	 * 타입뷰생성을위한 logList가져오기API
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public List<TrainingLog> getTrainingLogListByUserIdAndTypeId(int userId, Integer typeId) {
		
		return trainingMapper.selectTrainingLogListByUserIdAndTypeId(userId, typeId);
	}

	/**
	 * 일지리스트가져오기API
	 * @param userId
	 * @param typeId
	 * @return
	 */
	public List<TrainingLog> getTrainingLogListByUserIdAndTypeIdLimit(int userId, Integer typeId) {
		
		return trainingMapper.selectTrainingLogListByUserIdAndTypeIdLimit(userId, typeId, POST_MAX_SIZE);
	}
	
	// cnt 까지 실험
	/**
	 * 일지더보기API
	 * @param userId
	 * @param typeId
	 * @param cnt
	 * @return
	 */
	public List<TrainingLog> getTrainingLogListByUserIdAndTypeIdAndCnt(int userId, Integer typeId, int cnt) {
		
		int showLimitNum = POST_MAX_SIZE;
		int showLogNum = POST_MAX_SIZE * cnt;
		
		int sie = trainingMapper.selectTrainingLogListByUserIdAndTypeIdAndCnt(userId, typeId, showLogNum, showLimitNum).size();
		
		if (sie == 0) {
			return null;
		}
		
		return trainingMapper.selectTrainingLogListByUserIdAndTypeIdAndCnt(userId, typeId, showLogNum, showLimitNum);
	}

	/**
	 * 클릭한 일지상세가져오기 API
	 * @param logId
	 * @param userId
	 * @return
	 */
	public TrainingLog getTrainingLogByLogIdAndUserId(int logId, int userId) {
		
		return trainingMapper.selectTrainingLogByLogIdAndUserId(logId, userId);
		
	}

	/**
	 * 일지수정 API
	 * @param logId
	 * @param userId
	 * @param userEmail
	 * @param title
	 * @param successCheck
	 * @param problem
	 * @param content
	 * @param file
	 */
	public void updateLogByLogId(int logId, int userId, String userEmail, String title, int successCheck, String problem, String content, MultipartFile file) {
		
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

	/**
	 * 일지삭제 API
	 * @param logId
	 * @param userId
	 * @return
	 */
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
	
	/**
	 * 훈련기간이 종료된 타입의 상태를 변경
	 * @return
	 * @throws ParseException
	 */
	public int updateOverduedTypeList() throws ParseException {
		
		//typeList 를 DB에서 조회해옴
		List<TrainingType> typeList = trainingMapper.selectTrainingTypeList();
		
		// finishedAt을 담을 바구니 생성
		List<Date> finishedAtList = new ArrayList<>();
		
		// 기간이 지난 날짜만 담음
		List<Date> overduedList = new ArrayList<>();
		
		Date today = new Date();
		// sdf
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
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
