package com.dogdailylog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication 
public class DogDailyLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DogDailyLogApplication.class, args);
	}

}
