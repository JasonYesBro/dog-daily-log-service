package com.dogdailylog.training.bo;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.dogdailylog.training.model.TrainingType;

@SpringBootTest
class TrainingBOTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TrainingBO trainingBO;

	//@Test
	void test() {
		fail("Not yet implemented");
	}
	
	//@Transactional
	//@Test
	void 훈련타입생성() throws ParseException {
		logger.info("$$$$$$$$$$$ 훈련타입 생성 테스트 $$$$$$$$$$$");
		
		int rowCnt = 0;
		
		Date startedAt = new Date();
		Date finishedAt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		startedAt = sdf.parse("2021-12-09");
		finishedAt = sdf.parse("2022-01-10");
		
		rowCnt = trainingBO.addTrainingType(6, 1, "test훈련타입제목", startedAt, finishedAt);
		
		assertThat(rowCnt).isEqualTo(1);
	}
	
//	@Test
//	void 훈련타입리스트() {
//		logger.info("$$$$$$$$$$$$$$ 훈련타입리스트 테스트 $$$$$$$$$$$$$$$$$");
//		
//		List<TrainingType> list = new ArrayList<>();
//		list = trainingBO.getTrainingTypeListByUserId(6);
//		
//		assertThat(list.size()).isEqualTo(2);
//	}
}
