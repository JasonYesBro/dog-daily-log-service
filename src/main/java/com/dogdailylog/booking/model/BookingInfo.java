package com.dogdailylog.booking.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder(toBuilder = true)    // 필드 세팅, toBuilder=true: 기존 객체에 일부 필드값만 변경 가능
@NoArgsConstructor  // 파라미터 없는 생성자
@AllArgsConstructor // 모든 필드 있는 생성자
@Table(name = "booking_info")   // 테이블명과 클래스명이 동일하면 생략 가능
@Entity  // DB테이블 필수!
public class BookingInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // auto_increment 설정임
	private Long id;
	
	@Column(name="userId") // 카멜케이스는 직접 name값을 정의해주지않으면 snakecase로 찾으려 함
	private Integer userId;
	
	@Column(name="schoolId")
	private int schoolId;
	
	@Column(name="pickUpDate")
	private Date pickUpDate;
	
	@Column(name="pickUpTime")
	private String pickUpTime;
	
	@Column(name="bookedAt")
	private LocalDate bookedAt;
	
	private int price;

	private int status;
	
	@UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="createdAt", updatable = false) // 최초시간을 넣고 업데이트가 불가능하게 함
	private LocalDateTime createdAt;
	
	@UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="updatedAt")
	private LocalDateTime updatedAt;

}
