package kr.co.medicovid.dao.msk;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.HospitalInfoDTO;

public interface MainDAO {
	
	@Select("SELECT hname, hno, hy, hx, "
			+ "(6371 * acos(cos(radians(#{lat})) * cos(radians(hy)) * cos(radians(hx)"
			+ " - radians(#{lon})) + sin(radians(#{lat})) * sin(radians(hy)))) "
			+ "AS hcode "
			+ "FROM hospitalInfo "
			+ "HAVING hcode < 1 "
			+ "ORDER BY hcode ASC "
			+ "LIMIT 0,2")
	public List<HospitalInfoDTO> selectClosestHospitalsAll(String lat, String lon);
}
