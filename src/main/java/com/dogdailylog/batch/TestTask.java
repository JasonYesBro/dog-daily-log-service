package com.dogdailylog.batch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
	
	@Scheduled(cron="0 0 22 * * 0") // 분 | 시간 | 날짜 | 월 | 요일 | 명령 
	public void testTask() {
		// job 내용이 들어감
		// 훈련 종료 날짜를 기준으로 1주일 지났다 -> 삭제 1주일 마다 실행?
		
		// 오늘 날짜를 가져옴
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.format(date);

		// trainingType의 종료날짜를 가져옴
		
		// trainingType의 종료날짜 + 7일과 오늘날짜가 일치한다면 
		// 해당 trainingType 삭제
		// 
	}
}
