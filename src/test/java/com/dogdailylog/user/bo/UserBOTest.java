package com.dogdailylog.user.bo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
		logger.info("############ 중복확인테스트 #############");
		
		String name = "Jason J";
		
		User user = userBO.getUserByName(name);
		
		assertThat(name).isEqualTo(user.getName());
	}

	@Transactional // insert 후 다시 rollback
	//@Test
	void 회원가입() throws ParseException {
		logger.info("############# 회원가입테스트 ##############");
		
		Date adoptionDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		adoptionDate = formatter.parse("2022-10-10");
		
		// given
		User user = new User();
		
		user.setId(9);
		user.setName("hello");
		user.setPuppyName("willy");
		user.setLoginEmail("spsp@gmail.com");
		user.setPassword("123123");
		user.setSalt("123");
		user.setProfileImagePath("user/images");
		user.setAdoptionDate(adoptionDate);
		user.setSignUpType(0);
		
        // when
        userMapper.insertUser("spsp@gmail.com", "hello", "willy", "user/images", "123123", "123", adoptionDate, 0);

        // then
        User findUser = userBO.getUserByLoginEmail(user.getLoginEmail());
        
        assertThat(user.getName()).isEqualTo(findUser.getName());
	}
	
	//@Test
	void 로그인() {
		logger.info("############### 로그인테스트 ###############");
		// given
		User user = new User();
		user = userBO.getUserByLoginEmail("tmdgus5611@gmail.com");
		
		// when
		user = userBO.getUserByLoginEmailAndPassword(user.getLoginEmail(), user.getPassword());
		
		// then
		assertNotNull(user);
	}
	
	@Test
	void 비밀번호재설정() {
		// given
		User user = userBO.getUserByLoginEmail("tmdgus5611@gmail.com");
		String newPassword = "121111"; 
		
		if (ObjectUtils.isEmpty(user)) {
			logger.info("############## 유저 is null ##############");
			return;
		}
		
		// when
		// 비밀번호 변경
		if(userBO.resetPasswordByUserEmail(newPassword, user.getLoginEmail()) > 0) {
			
			// then
			// 변경한 뒤의 유저
			User AfterResetPasswordUser = userBO.getUserByLoginEmail(user.getLoginEmail());
			
			logger.info("############## {}, {}", user.getPassword(), AfterResetPasswordUser.getPassword());
			
			// 기댓값과 실제값이 다르면 성공
			assertNotEquals(user.getPassword(), AfterResetPasswordUser.getPassword());
		}
				
	}
	
}
