package com.dogdailylog.payment.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder(toBuilder = true)  
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_info")
@Entity
public class PaymentInfo {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="bookingId")
	private Long bookingId;
	
	@Column(name="userId")
	private Integer userId;
	
	private int price;
	
	@Column(name="payment")
	private String payment;
	
	@Column(name="approval", columnDefinition = "TINYINT", length=1)
	private int approval;
	
	@UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="createdAt", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp    // 현재시간 디폴트값
    @Column(name="updatedAt")
	private LocalDateTime updatedAt;

}
