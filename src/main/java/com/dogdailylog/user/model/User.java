package com.dogdailylog.user.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class User {
	private int id;
	private String loginEmail;
	private String password;
	private String salt;
	private String name;
	private String puppyName;		
	private String profileImagePath	;		
	private Date adoptionDate;
	private Date createdAt;
	private Date updatedAt;

	
	@Builder
    public User(int id, String loginEmail, String password, String salt, String name, String puppyName, Date adoptionDate, String profileImagePath){
        this.id = id;
		this.loginEmail = loginEmail;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.puppyName = puppyName;
        this.adoptionDate = adoptionDate;
        this.profileImagePath = profileImagePath;
    }
}
