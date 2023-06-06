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
			, @Param("salt") String salt
			, @Param("adoptionDate")  Date adoptionDate
			, @Param("signUpType") int signUpType);

	public User selectUserByName(String name);
	
	public User selectUserByLoginEmail(String email);
	
	public User selectUserById(Integer id);

	public User selectUserByLoginEmailAndPassword(
			@Param("email") String email
			, @Param("password") String password);

	public int updatePasswordByUserEmail(
			@Param("email") String email
			, @Param("salt") String salt
			, @Param("password") String password);

}
