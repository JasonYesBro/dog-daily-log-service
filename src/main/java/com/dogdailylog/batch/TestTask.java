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
	
	@Scheduled(cron="15 47 20 * * *") //초 | 분 | 시간 | 날짜 | 월 | 요일 
	public void testTask() {
		try {
			int updateRowCnt = 0;
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
