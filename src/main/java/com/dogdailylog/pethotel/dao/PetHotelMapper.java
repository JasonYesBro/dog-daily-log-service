package com.dogdailylog.pethotel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dogdailylog.pethotel.model.PetHotel;

@Repository
public interface PetHotelMapper {
	public PetHotel selectPetHotelById(int id);

	public int insertPetHotel(
			@Param("name") String name
			, @Param("tel") String tel
			, @Param("address") String address);

	public List<PetHotel> selectPetHotelList();
}
