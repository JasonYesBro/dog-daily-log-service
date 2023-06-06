package com.dogdailylog.user.bo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.common.FileManagerService;
import com.dogdailylog.config.Encrypt;
import com.dogdailylog.user.dao.UserMapper;
import com.dogdailylog.user.model.User;

@Service
public class UserBO {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	public int addUser(String email, String name, String puppyName, MultipartFile file, String password, String salt, Date adoptionDate, int signUpType) {
		String profileImagePath = null;
		if (file != null) {
			// 서버에 이미지 업로드 후 profileImagePath 받아옴
			profileImagePath = fileManager.saveFile(email, file);
		}
		int result = userMapper.insertUser(email, name, puppyName, profileImagePath, password, salt, adoptionDate, signUpType);
		return result;
	}

	public User getUserByName(String name) {
		User user = null;
		user = userMapper.selectUserByName(name);
		return user;
	}
	
	// getUser 가 아닌 getSalt 가 맞음 + getUser를 하나더 생성 ByLoginEmail
	public String getSaltByLoginEmail(String email) {
		User user = userMapper.selectUserByLoginEmail(email);
		return user.getSalt();
	}
	
	public User getUserByLoginEmail(String email) {
		User user = null;
		user = userMapper.selectUserByLoginEmail(email);
		return user;
	}
	
	public User getUserById(Integer id) {
		User user = null;
		user = userMapper.selectUserById(id);
		return user;
	}

	public User getUserByLoginEmailAndPassword(String email, String password) {
		User user = null;
		user = userMapper.selectUserByLoginEmailAndPassword(email, password);
		return user;
	}

	public int resetPasswordByUserEmail(String email, String password) {
		// salt의 값이랑 password 둘다 변경해줘야 함
		String salt = Encrypt.getSalt();
		String hashedPassword = Encrypt.md5(password, salt);

		return userMapper.updatePasswordByUserEmail(email, salt, hashedPassword);
	}
}
