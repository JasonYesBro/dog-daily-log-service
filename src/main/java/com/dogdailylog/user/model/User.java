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
	private String name;
	private String puppyName;		
	private String profileImagePath	;		
	private Date adoptionDate;
	private Date createdAt;
	private Date updatedAt;

	
	@Builder
    public User(String loginEmail, String password, String name, String puppyName, Date adoptionDate, String profileImagePath){
        this.loginEmail = loginEmail;
        this.password = password;
        this.name = name;
        this.puppyName = puppyName;
        this.adoptionDate = adoptionDate;
        this.profileImagePath = profileImagePath;
    }
}
