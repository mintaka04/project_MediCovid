package kr.co.medicovid.dao.hgh;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.HospitalInfoDTO;

public interface HosDetailDAO {

	//해당 병원 정보 가져오기
	@Select("SELECT * FROM hospitalInfo WHERE hno = #{hno}")
	HospitalInfoDTO selectHospital(int hno);
	
	//해당 병원 카테고리번호 전부 가져오기(여러개 분류도 존재)
	@Select("SELECT ccategory FROM hospitalCategory WHERE hno = #{hno}")
	List<Integer> getCategory(int hno);
	
	//예약만들기
	@Insert("INSERT INTO reservation VALUES(null, #{rdate}, #{rtime}, #{rname}, #{rtel}, #{rremark}, 1, "
			+ "#{uno}, #{hno} )")
	void makeReserv(@Param("rdate")String rdate, @Param("rtime")String rtime, 
			@Param("rname")String rname, @Param("rtel")String rtel, @Param("rremark")String rremark, 
			@Param("uno")int uno, @Param("hno")int hno);
}
