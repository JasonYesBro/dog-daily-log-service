package com.dogdailylog.training.bo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.training.dao.TrainingMapper;
import com.dogdailylog.training.model.TrainingLog;
import com.dogdailylog.training.model.TrainingTypeView;

@SpringBootTest
class TrainingBOTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TrainingBO trainingBO;
	
	@Autowired
	TrainingMapper trainingMapper;

	//@Test
	void test() {
		fail("Not yet implemented");
	}

	//@Transactional
	//@Test
	void 훈련타입생성() throws ParseException {
		logger.info("$$$$$$$$$$$ 훈련타입 생성 테스트 $$$$$$$$$$$");
		
		int rowCnt = 0;
		
		// giver
		Date startedAt = new Date();
		Date finishedAt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		startedAt = sdf.parse("2021-12-09");
		finishedAt = sdf.parse("2022-01-10");
		
		// when
		rowCnt = trainingBO.addTrainingType(6, 1, "test훈련타입제목", startedAt, finishedAt);
		
		// then
		assertThat(rowCnt).isEqualTo(1);
	}
	
	// @Test
	void 훈련타입리스트() {
		logger.info("$$$$$$$$$$$$$$ 훈련타입리스트 테스트 $$$$$$$$$$$$$$$$$");
		
		List<TrainingTypeView> list = new ArrayList<>();
		
		// when
		list = trainingBO.getTrainingTypeViewListByUserId(6);
		
		// then
		assertThat(list.size()).isEqualTo(3);
	}
	
	@Transactional
	@Test
	void 종료된훈련타입() throws ParseException {
		logger.info("$$$$$$$$$$$$$$ 종료된훈련타입 테스트 $$$$$$$$$$$$$$$$$");
		
		List<TrainingTypeView> list = new ArrayList<>();
		
		// when
		list = trainingBO.getTrainingTypeViewListByUserId(6);
		
		// cron 설정 - 설정시간 후 변경이 됐는지 테스트?
		int cnt = trainingBO.updateOverduedTypeList();
		
		// then
		assertThat(cnt).isEqualTo(3);
	}
	
//	@Transactional
//	@Test
	void 훈련로그생성() {
		logger.info("$$$$$$$$$$$$$$ 훈련로그생성 테스트 $$$$$$$$$$$$$$$$$");
		TrainingLog log = new TrainingLog();
		
		// when
		int rowCnt = trainingMapper.insertTrainingLog(6, 1, "test", 1, "손을 깨물어요.", "손을 깨물지 않게 아픈척을 해봤어요.", "logImage");
		
		// then
		if(rowCnt > 0) {
			log = trainingBO.getTrainingLogByLogIdAndUserId(28, 6);
			assertNotNull(log);
		}
	}
	
	//@Transactional
	//@Test
	void 훈련로그삭제() {
		logger.info("$$$$$$$$$$$$$$ 훈련로그삭제 테스트 $$$$$$$$$$$$$$$$$");
		
		// when
		int rowCnt = trainingBO.deleteLogByLogIdAndUserId(27, 6);
		
		// then
		assertEquals(rowCnt, 1);
	}
	
//	@Transactional
//	@Test
	void 훈련일지수정하기() {
		logger.info("$$$$$$$$$$$$$$ 훈련일지수정하기 테스트 $$$$$$$$$$$$$$$$$");
		
		// when
		TrainingLog beforeLog = trainingBO.getTrainingLogByLogIdAndUserId(27, 6);
		
		trainingMapper.updateLogByLogId(beforeLog.getId(), "수정테스트", 1, "훈련원인", "수정테스트", "log/image");
		TrainingLog afterLog = trainingBO.getTrainingLogByLogIdAndUserId(27, 6);
		
		logger.info("$$$$$$$$$$ 수정 전 제목 : {}, 수정 후 제목 : {}", beforeLog.getTitle(), afterLog.getTitle());
		
		// then 같지 않으면 성공
		assertNotSame(beforeLog, afterLog);
	}

}
