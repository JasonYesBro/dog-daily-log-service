package com.dogdailylog.pethotel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dogdailylog.booking.dao.BookingRepository;
import com.dogdailylog.booking.model.BookingInfo;
import com.dogdailylog.pethotel.bo.PetHotelBO;
import com.dogdailylog.pethotel.model.PetHotel;

@Controller
@RequestMapping("/hotel")
public class PetHotelController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PetHotelBO petHotelBO;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	// TODO bo로 로직 refactor	
	@RequestMapping("/list_view")
	public String petHotelView(Model model) throws ParseException {
		List<PetHotel> hotelList = new ArrayList<>();
		
		hotelList = petHotelBO.getPetHotelList();
	
		model.addAttribute("hotelList", hotelList);
		model.addAttribute("view", "pethotel/petHotelList");
		
		return "template/layout";
	}
	
	@GetMapping("/detail_view")
	public String hotelDetail(
			Model model
			, @RequestParam("id") int id
			, HttpSession session) {
		
//		int id = Integer.parseInt(num);
		String name = (String) session.getAttribute("userName");
		// TODO 예약자 정보까지 넘겨야함
		PetHotel petHotel = petHotelBO.getPetHotelById(id);
		
		model.addAttribute("petHotel", petHotel);
		model.addAttribute("name", name);
		model.addAttribute("view", "pethotel/hotelDetail");
		
		return "template/layout";
	}

	@GetMapping("/history_view")
	public String bookingHistoryView(
			Model model
			, HttpSession session) {
		
		int userId = (int) session.getAttribute("userId");
		
		List<BookingInfo> bookingList = bookingRepository.findAllByUserId(userId);
		
		model.addAttribute("bookingList", bookingList);
		model.addAttribute("view", "pethotel/bookingHistory");
		
		return "template/layout";
	}
}
