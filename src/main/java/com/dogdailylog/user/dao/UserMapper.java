package com.dogdailylog.user.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.user.model.User;

@Repository
public interface UserMapper {

	public int insertUser(
			@Param("email")  String email
			, @Param("name") String name
			, @Param("puppyName") String puppyName
			, @Param("profileImagePath") String profileImagePath
			, @Param("password")  String password
			, @Param("adoptionDate")  Date adoptionDate);

	public User selectUserByName(String name);

}
