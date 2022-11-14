package kr.co.medicovid.dao.kjh;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import kr.co.medicovid.dto.hospitalReservationDTO;

public interface MyinfoDAO {

	// 병원이름, 병원주소, 예약번호, 예약일시, 예약시간 가져오기(진행중인 예약용)
	@Select("SELECT reservation.hno, hname, haddress, rno, rdate, rtime, hx, hy FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus != 3 ")
	public List<hospitalReservationDTO> selecthospitalInfo(int uno);
	
	// 병원이름, 병원주소, 예약번호, 예약일시, 예약시간 가져오기(지난 예약 내역용)
	@Select("SELECT reservation.hno, hname, haddress, rno, rdate, rtime, hx, hy FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus = 3 ")
	public List<hospitalReservationDTO> selecthospitalpassedInfo(int uno);
	
	// 진행중인 예약 리스트 길이
	@Select("SELECT COUNT(*) FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus != 3 ")
	public String selecthospitalIngNumInfo(int uno);
	
	// 진행중인 예약 list 두개만
	@Select("SELECT reservation.hno, hname, haddress, rno, rdate, rtime, hx, hy FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus != 3  LIMIT 2 ")
	public List<hospitalReservationDTO> hospitalIngInfoBringtwo(int uno);
	
	// 지난 예약 내역 리스트 길이
	@Select("SELECT COUNT(*) FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus = 3 ")
	public String selecthospitalpassedNumInfo(int uno);
	
	// 지난 예약 내역 list 두개만
	@Select("SELECT reservation.hno, hname, haddress, rno, rdate, rtime, hx, hy FROM hospitalInfo, reservation, users WHERE hospitalInfo.hno = reservation.hno AND reservation.uno = users.uno AND reservation.uno = #{uno} AND reservation.rstatus = 3  LIMIT 2 ")
	public List<hospitalReservationDTO> hospitalpassedInfoBringtwo(int uno);
	
	// 예약 취소
	@Delete("DELETE FROM reservation WHERE rno = #{rno} ")
	public void cancle(int rno);
}