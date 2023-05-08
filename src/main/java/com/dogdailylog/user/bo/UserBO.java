package com.dogdailylog.user.bo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dogdailylog.common.FileManagerService;
import com.dogdailylog.user.dao.UserMapper;

@Service
public class UserBO {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	public int addUser(String email, String name, String puppyName, MultipartFile file, String password, Date adoptionDate) {
		String profileImagePath = null;
		if (file != null) {
			// 서버에 이미지 업로드 후 profileImagePath 받아옴
			profileImagePath = fileManager.saveFile(email, file);
		}
		int result = userMapper.insertUser(name, puppyName, profileImagePath, email, password, adoptionDate);
		return result;
	}
}
