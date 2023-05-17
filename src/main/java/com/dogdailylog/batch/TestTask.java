package com.dogdailylog.batch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dogdailylog.training.bo.TrainingBO;
import com.dogdailylog.training.model.TrainingType;

@Component
public class TestTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TrainingBO trainingBO;
	
	@Scheduled(cron="45 37 21 * * *") //초 | 분 | 시간 | 날짜 | 월 | 요일 
	public void testTask() {
		// job 내용이 들어감
		// 훈련 종료 날짜를 기준으로 1주일 지났다 -> 삭제 1주일 마다 실행?
		// 오늘 날짜를 가져옴
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date1 = sdf.format(date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String strDate = sdf.format(date);

		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			logger.debug(strDate);
		}
		
		
		// trainingType의 종료날짜를 가져옴
		List<TrainingType> trainingTypeList = new ArrayList<>();
		
		try {
			trainingBO.getTrainingTypeList();
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//trainingBO.getTrainingTypeList();
		
		// trainingType의 종료날짜 + 7일과 오늘날짜가 일치한다면 
		// 해당 trainingType 삭제
		// 
	}
}
