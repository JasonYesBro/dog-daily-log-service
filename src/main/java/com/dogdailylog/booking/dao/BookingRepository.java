package com.dogdailylog.booking.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dogdailylog.booking.model.BookingInfo;

@Repository
public interface BookingRepository extends JpaRepository<BookingInfo, Long>  {
	// JPQL 네이티브 쿼리를 쓰지 않아도 메서드명 통해 원하는 데이터를 조회해 올 수 있음(칼럼명과 동일해야함)
	Optional<BookingInfo> findByUserIdAndId(Integer userId, Long id);

	// findAll로 변경
	List<BookingInfo> findAllByUserId(Integer userId);
}
