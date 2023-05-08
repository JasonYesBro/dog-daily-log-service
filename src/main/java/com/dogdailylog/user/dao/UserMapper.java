package com.dogdailylog.user.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

	public int insertUser(
			@Param("name") String name
			,@Param("puppyName") String puppyName
			,@Param("email")  String email
			,@Param("password")  String password
			,@Param("profileImagePath") String profileImagePath
			, @Param("adoptionDate")  Date adoptionDate);

}
