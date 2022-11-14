package kr.co.medicovid.dao.hjj;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.HospitalInfoDTO;

@Mapper
public interface HospDAO {

	
	// 병원 하나 가져오기
	@Select("select * from hospitalInfo where hname = #{hname}")
	HospitalInfoDTO loginHosp(@Param("hname")String hname);
}
