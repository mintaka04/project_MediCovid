package kr.co.medicovid.dao.lsh;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.UsersDTO;

public interface AdminDAO {

	// 일반사용자 뒤에서부터 10개만 가져오기
	@Select("SELECT uno, uid, upw, uname, utel, uloginType, penalty FROM users ORDER BY uno DESC LIMIT #{startNo}, 10")
	public List<UsersDTO> selectUsersAll(int startNo);

	// 병원 뒤에서부터 10개만 가져오기
	@Select("SELECT hno, hpw, hname, hgu, hcode, haddress, htel, htime, hx, hy FROM hospitalInfo ORDER BY hno DESC LIMIT #{startNo}, 10")
	public List<HospitalInfoDTO> selectHospitalsAll(int startNo);
	
	// 병원 뒤에서부터 1개만 가져오기(방금 등록한 병원 가져오기)
	@Select("SELECT hno, hpw, hname, hgu, hcode, haddress, htel, htime, hx, hy FROM hospitalInfo ORDER BY hno DESC LIMIT 1")
	public HospitalInfoDTO selectJustAddedHospital();
	
	// 병원번호 조건에 맞는 병원만 1개 가져오기
	@Select("SELECT hno, hpw, hname, hgu, hcode, haddress, htel, htime, hx, hy FROM hospitalInfo WHERE hno = #{hno}")
	public HospitalInfoDTO selectOne(int hno);
	
	// 사용자 아이디 조건에 맞는 사용자만 가져오기
	@Select("SELECT uno, uid, upw, uname, utel, uloginType, penalty FROM users WHERE uid LIKE CONCAT('%',#{uid},'%') LIMIT #{startNo}, 10")
	public List<UsersDTO> selectByUid(String uid, int startNo);
	
	// 총 병원 갯수 가져오기
	@Select("SELECT count(*) FROM hospitalInfo")
	public int getTotalHospitalCount();	
	
	// 총 유저 수 가져오기
	@Select("SELECT count(*) FROM users")
	public int getTotalUserCount();
	
	// 검색어에 해당하는 유저 수 가져오기
	@Select("SELECT count(*) FROM users WHERE uid LIKE CONCAT('%',#{uid},'%')")
	public int getUserCountByUid(String uid);

	// 새 병원 등록
	@Insert("INSERT INTO hospitalInfo VALUES(0, '0000', #{hname}, #{hgu}, 31, #{haddress}, #{htel}, #{htime}, #{hx}, #{hy}, 10)")
	public void insertNewHospital(HospitalInfoDTO dto);

	// 새 병원 카테고리 등록
	@Insert("INSERT INTO hospitalCategory VALUES(null, #{ccategory}, #{hno})")
	public void insertHospitalCategory(int ccategory, int hno);

	// 일반 사용자 패널티 부여
	@Update("UPDATE users SET penalty = #{penalty} WHERE uno = #{uno}")
	public void modifyPenalty(int penalty, int uno);
	
	// 병원 정보 수정
	@Update("UPDATE hospitalInfo SET hname = #{hname}, hgu = #{hgu}, haddress = #{haddress}, htel = #{htel}, htime = #{htime}, hx = #{hx}, hy = #{hy} WHERE hno = #{hno}")
	public void updateNewHospital(HospitalInfoDTO dto);
	
	// 병원 삭제
	@Delete("DELETE FROM hospitalInfo WHERE hno = #{hno}")
	public void deleteHospital(int hno);
	
	// 병원 카테고리 삭제
	@Delete("DELETE FROM hospitalCategory WHERE hno = #{hno}")
	public void deleteHospitalCategory(int hno);
	
	// 유저 삭제
	@Delete("DELETE FROM users WHERE uno = #{uno}")
	public void deleteUser(int uno);
	

}