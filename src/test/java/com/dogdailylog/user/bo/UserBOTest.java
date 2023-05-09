package com.dogdailylog.user.bo;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dogdailylog.user.dao.UserMapper;
import com.dogdailylog.user.model.User;

@SpringBootTest
class UserBOTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired // @SpringBootTest 가 있으므로 작동가능
	UserMapper userMapper;
	
	@Autowired
	UserBO userBO;
	
	//@Test
	void test() {
		fail("Not yet implemented");
	}
	
	//@Test
	void 닉네임중복확인() {
		logger.info("############ 중복확인 #############");
		
		String name = "jason";
		
		User user = userBO.getUserByName(name);
		assertNull(user);
	}

	@Transactional // insert 후 다시 rollback
	//@Test
	void 회원가입() throws ParseException {
		logger.info("############# 회원가입 ##############");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		date = formatter.parse("2023-09-10");
		
		userMapper.insertUser("test12", "aaaa12", "test12@test.com", "1223123", "user/images", date);
	}
	
	
	
}
