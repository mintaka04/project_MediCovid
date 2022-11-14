package kr.co.medicovid.dao.hgh;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.ReservationDTO;

public interface HosAdminDAO {
//테스트용 파일
	
	//예약 상태에 따라 출력
	@Select("SELECT * FROM reservation where hno = #{hno} and rdate > #{dayafter} and rstatus = 3 order by rdate desc")
	List<ReservationDTO> selectStatus(@Param("dayafter")String dayafter, @Param("hno")int hno);
	
	//일일예약관리.
	@Select("SELECT * FROM reservation where hno = #{hno} and rdate = #{today}")
	List<ReservationDTO> selectDate(@Param("today")String today, @Param("hno")int hno);
	
	//내방 상태 변경
	@Update("UPDATE reservation SET rstatus = #{rstatus} WHERE rno = #{rno}")
	void changeStatus(@Param("rstatus") int rstatus, @Param("rno") int rno);
	
	//과거내역출력
	@Select("SELECT * FROM reservation where hno = #{hno} and rdate > #{dayafter} and rstatus = 3 order by rdate desc")
	List<ReservationDTO> selectPastRecord(@Param("dayafter")String dayafter, @Param("hno")int hno);
	
	
	//예약인원수정할때 병원정보 가져오기
	@Select("SELECT * FROM hospitalInfo WHERE hno = #{hno}")
	HospitalInfoDTO selectHospital(int hno);
	
	
	//SELECT COUNT(*) FROM reservation WHERE rdate = '2022-07-19' AND rtime = '09:00:00';
	//날짜+시간별 예약한 인원 출력
	@Select("SELECT COUNT(*) FROM reservation WHERE hno = #{hno} AND rdate = #{rdate} AND rtime = #{rtime}")
	int dateTimeResvNum(@Param("hno")int hno, @Param("rdate")String rdate, @Param("rtime")String rtime);
	
	
	//날짜+시간별 예약 가능한 총 인원 출력->해당 데이터 없을 경우 병원에서 기본저장해둔 값으로 출력.
	@Select("SELECT ifnull(MAX(tpeople), (SELECT hrevptime FROM hospitalInfo WHERE hno = #{hno})) tpeople FROM reserveTime WHERE hno = #{hno} AND ttime = #{rtime} AND tdate = #{rdate}") 
	int dateTimeResvTotalNum(@Param("hno")int hno, @Param("rdate")String rdate, @Param("rtime")String rtime);
	
	
	//예약인원수변경 존재하는지 확인. 존재시 숫자 tno, 존재하지 않을 시 false리턴
	@Select("SELECT ifnull(MAX(tno), 'false') tpeople FROM reserveTime WHERE hno = #{hno} AND ttime = #{rtime} AND tdate = #{rdate}")
	String returnBoolean(@Param("hno")int hno, @Param("rdate")String rdate, @Param("rtime")String rtime);
	
	//존재x시 삽입
	@Insert("INSERT INTO reserveTime VALUES(null, #{rtime}, #{selNum}, #{hno}, #{rdate})")
	void makeRevTime(@Param("hno")int hno, @Param("rdate")String rdate, @Param("rtime")String rtime, @Param("selNum")String selNum);
	
	
	//존재시 update
	@Update("UPDATE reserveTime SET tpeople = #{selNum} WHERE tno = #{tno}")
	void updateRevTime(@Param("selNum")String selNum, @Param("tno")int tno);
	
	
	//해당 병원 비밀번호 가져오기
	@Select("SELECT hpw FROM hospitalInfo WHERE hno = #{hno}")
	String getPW(@Param("hno")int hno);
	
	
	//해당병원 정보 수정
	@Update("UPDATE hospitalInfo SET hpw=#{hpw}, hname=#{hname}, hgu=#{hgu}, haddress=#{haddress}, htel=#{htel}, htime=#{htime}, hx=#{hx}, hy=#{hy}, hrevptime=#{hrevptime} WHERE hno = #{hno}")
	void updateHopInfo(@Param("hpw")String hpw, @Param("hname")String hname, @Param("hgu")String hgu, @Param("haddress")String haddress, @Param("htel")String htel, @Param("htime")String htime,
						@Param("hx")String hx, @Param("hy")String hy, @Param("hrevptime")int hrevptime, @Param("hno")int hno);
	
	
	
}