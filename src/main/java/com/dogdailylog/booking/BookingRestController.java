package com.dogdailylog.booking;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dogdailylog.booking.bo.BookingBO;

@RestController
@RequestMapping("/booking")
public class BookingRestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BookingBO bookingBO;
	
	/**
	 * 예약하기 API
	 * @param bookingId
	 * @param bookedAt
	 * @param session
	 * @return
	 */
//	@PostMapping("/create")
//	public Map<String, Object> createBooking(
//			@RequestParam("schoolId") int schoolId
//			, @RequestParam("pickUpDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date pickUpDate
//			, @RequestParam("pickUpTime") String pickUpTime
//			, @RequestParam("price") int price
//			, HttpSession session) {
//		
//		Map<String, Object> result = new HashMap<>();
//		
//		// session 에서 user 정보
//		int userId = (int) session.getAttribute("userId");
//		
//		// DB insert
//		int rowCnt = 0;
//		
//		try {
//			rowCnt = bookingBO.addBooking(userId, schoolId, pickUpDate, pickUpTime, price);
//		} catch (ParseException e) {
//			logger.debug("########## date parse error ########## {}", pickUpDate);
//		}
//		
//		// 반환된 행의 갯수로 분기
//		if (rowCnt > 0) {
//			result.put("code", 1);
//			result.put("result", "예약에 성공하였습니다.");
//		} else {
//			result.put("code", 500);
//			result.put("errorMessage", "예약에 실패하였습니다.");
//		}
//		
//		return result;
//	}
	
}
