package com.dogdailylog.batch;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dogdailylog.training.bo.TrainingBO;

@Component
public class TestTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TrainingBO trainingBO;
	
	@Scheduled(cron="10 05 18 * * *") //초 | 분 | 시간 | 날짜 | 월 | 요일 
	public void testTask() {
		// job 내용이 들어감
		// 훈련 종료 날짜를 기준으로 1주일 지났다 -> 삭제 1주일 마다 실행?
		try {
			int updateRowCnt = 0;
			//deleteRowCnt = trainingBO.deleteOverduedTypeList();
			updateRowCnt = trainingBO.updateOverduedTypeList();
			if ( updateRowCnt > 0 ) {
				logger.info("############## 기간이 지난 훈련은 종료처리되었습니다. ############## 처리된 행의 갯수 : {}", updateRowCnt);
			} else {
				logger.info("############## 종료처리할 훈련이 없습니다. ##############");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
