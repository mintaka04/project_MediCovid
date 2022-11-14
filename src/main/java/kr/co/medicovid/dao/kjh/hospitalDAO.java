package kr.co.medicovid.dao.kjh;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.HospitalInfoDTO;

public interface hospitalDAO {

	@Select("SELECT * FROM hospitalInfo")
	List<HospitalInfoDTO> selectAll();
}
