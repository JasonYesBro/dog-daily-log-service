package com.dogdailylog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // batch 사용하기 위한 어노테이션
@SpringBootApplication 
public class DogDailyLogApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DogDailyLogApplication.class, args);
	}

}
