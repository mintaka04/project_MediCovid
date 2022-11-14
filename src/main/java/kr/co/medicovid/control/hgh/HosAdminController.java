package kr.co.medicovid.control.hgh;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.hgh.HosAdminDAO;
import kr.co.medicovid.dao.hgh.HosDetailDAO;
import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.ReservationDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;
import kr.co.medicovid.service.hgh.RevPerTimeService;

@Controller
public class HosAdminController {

	@Autowired
	HosAdminDAO dao;
	
	@Autowired
	HosDetailDAO ddao;
	
	// springSecurity 에서 로그인 할 때 사용하는 패스워드 암호화
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	@Resource
	RevPerTimeService rpt;

	
	
	@RequestMapping("/HospitalMain")
	public ModelAndView hello(@AuthenticationPrincipal PrincipalDetails principalDetails,ModelAndView mav) {

		/* mav.setViewName("hosAdmin/hospitalMain"); */

		if(principalDetails == null) { // 세션 없을 때 ===> 로그인 안된 경우
			mav.setViewName("loginForm");   		
			System.out.println("로그인화면으로 전환");
		}else {
			if(principalDetails.getType().equals("user")) {
				mav.addObject("model", principalDetails.getUdto());
				System.out.println("사용자로 로그인됨");
				
			}else if(principalDetails.getType().equals("hosp")) {
				//병원관리자로 로그인되었을 때만 값 전달되도록
				mav.addObject("model", principalDetails.getHdto());
				System.out.println("병원관리자로 로그인");
				
				//병원번호 가져오기.
				int hno = principalDetails.getHdto().getHno();
				System.out.println(hno);
				
				// 오늘 날짜
				// LocalDateTime now = LocalDateTime.now();
				LocalDate now = LocalDate.now();
				
				//7일전 내역부터만 출력
				LocalDate date = LocalDate.now().minusDays(8);
				DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String dayafter = date.format(df2);
				mav.addObject("pastResvRecord", dao.selectStatus(dayafter, hno));
				
				
				// 오늘 날짜에 해당하는 내역리스트 가져오기.
				mav.addObject("dateResvRecord", dao.selectDate(now.toString(), hno));
				
				// 오늘 날짜 형식 바꿔줌.
				DateTimeFormatter df = DateTimeFormatter.ofPattern("M월 d일");
				String nowString = now.format(df);
				mav.addObject("currentDate", nowString);
					
				
			}
			mav.setViewName("hosAdmin/hospitalMain");
		}

		return mav;
	}

	
	
	
	// 날짜관련 ajax
	@RequestMapping("/HospitalMainDatePlus")
	@ResponseBody // json 형태로 리턴하기 위함..
	public ModelAndView datePlus(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, @RequestParam int hiddendate, ModelAndView mav) {

		//병원번호 가져오기.
		mav.addObject("model", principalDetails.getHdto());	
		int hno = principalDetails.getHdto().getHno();
		
		
		// hidden 값만큼 증가시킨 날짜->출력형식으로 바꿔서 model에 저장
		LocalDate dateadded = LocalDate.now().plusDays(hiddendate);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("M월 d일");
		String dateresult = dateadded.format(df);
		mav.addObject("dateresult", dateresult);


		// 날짜에 해당하는 리스트를 model에 저장
		List<ReservationDTO> dateList =  dao.selectDate(dateadded.toString(), hno);
		mav.addObject("dateList", dateList);
		
		
		//오늘이면 내방확인 버튼 있는 곳으로, 오늘 아니면 버튼 없는 곳으로 보내기
		if(dateadded.equals(LocalDate.now())) {
			System.out.println("오늘임");
			mav.setViewName("hosAdmin/hopMainAjaxToday");			
		}else {
			System.out.println("오늘아님");
			mav.setViewName("hosAdmin/hopMainAjaxNotToday");			
		}
		
		return mav;
	}
	
	
	
	
	//내방확인용 ajax
	@RequestMapping("/HospitalVisitConfirm")
	public void visitConfirm(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, @RequestParam String visitstatus, @RequestParam int rno) {
		
		
		//내방완료 상태로 변경해주기
		if(visitstatus.equals("visited")) {
			
			dao.changeStatus(3, rno);			
		
		//취소불가 상태로 변경해주기
		}else if(visitstatus.equals("cancel")) {
			
			dao.changeStatus(2, rno);	
			
		}
	}
	
	
	
	
	//과거기록 확인용 ajax
	@RequestMapping("/HospitalPastResult")
	public ModelAndView printPastResult(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, @RequestParam String pastday, ModelAndView mav) {

		
		//병원번호 가져오기.
		mav.addObject("model", principalDetails.getHdto());	
		int hno = principalDetails.getHdto().getHno();		
		
		LocalDate date = LocalDate.now();

		
		if(pastday.equals("radio7")) {
			date = LocalDate.now().minusDays(8);
			System.out.println("날짜 : " + date);
			
		}else if(pastday.equals("radio30")) {			
			date = LocalDate.now().minusDays(31);
			System.out.println("날짜 : " + date);			
			
		}else {
			date = LocalDate.now().minusDays(91);
			System.out.println("날짜 : " + date);			
		}

		
		//이 날짜 이후부터 출력함.
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dayafter = date.format(df);


		List<ReservationDTO> dateList = dao.selectPastRecord(dayafter, hno);
		mav.addObject("dateList", dateList);
		
		//찾아갈 view 이름
		mav.setViewName("hosAdmin/hopPastResultAjax");
		
		return mav;
	}
	
	
	
	//병원정보 수정 페이지
	@RequestMapping("/HospitalInfoSet")
	public ModelAndView hopInfoSetting(@AuthenticationPrincipal PrincipalDetails principalDetails, ModelAndView mav) {
		
		mav.addObject("model", principalDetails.getHdto());	
		mav.setViewName("hosAdmin/setHospitalInfo");
		
		//병원 분류 가져오기
		int hno = principalDetails.getHdto().getHno();		
//		System.out.println("시험중 hno : " + hno);
//		System.out.println("나오려나 : " + ddao.getCategory(hno));
		
		boolean[] arr = new boolean[3];
		
		for(int a : ddao.getCategory(hno)) {
			if(a == 1) {
				arr[0] = true;
				System.out.println("호흡기");
			}else if(a == 2) {
				arr[1] = true;
				System.out.println("재택");
			}else if(a==3) {
				arr[2] = true;
				System.out.println("전화");
			}
		}
		mav.addObject("hospitalCat", arr);
		
		return mav;
	}
	
	
	
	//병원정보수정페이지-ajax로 비밀번호 일치 확인
	@RequestMapping("/HopInfoPWCheck")
	@ResponseBody
	public String pwCheck(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, @Param("pw") String pw, @Param("hno")int hno) {
		String result = rpt.checkpw(pw, hno);
		System.out.println("컨트롤러 확인"+result);
		return result;
	}
	
	
	
	//병원정보 수정시 테이블 수정후 
	@RequestMapping("/hopTableEdit")
	public String hopTableEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, HospitalInfoDTO dto) {
		System.out.println(dto);
		
		dto.setHpw(bCryptPasswordEncoder.encode(dto.getHpw()));
		
		dao.updateHopInfo(dto.getHpw(), dto.getHname(), dto.getHgu(), dto.getHaddress(), dto.getHtel(), dto.getHtime(), dto.getHx(), dto.getHy(), dto.getHrevptime(), dto.getHno());
		
		return "forward:/HospitalMain";
	}
	
	
	//병원예약인원수 조정 페이지
	@RequestMapping("/HospitalRevPerTime")
	public ModelAndView patientPerTime(@AuthenticationPrincipal PrincipalDetails principalDetails, ModelAndView mav) {
		
		//병원번호 가져오기.
		mav.addObject("model", principalDetails.getHdto());	
		int hno = principalDetails.getHdto().getHno();
		
		mav.setViewName("hosAdmin/hopRevPerTime");
		
		//병원관련 정보
		HospitalInfoDTO dto1 = dao.selectHospital(hno);
		
		//병원관련모든 정보들전달
		mav.addObject("hospitalInfo", dto1);
		
		//운영시간간 중 시작시간 string 처리
		List<String> startTime = rpt.startTime(dto1.getHtime());
		mav.addObject("startTimes", startTime);
		
		//운영시간 중 끝시간 string 처리
		mav.addObject("endTimes", rpt.endTime(dto1.getHtime()));
		
		//+15일까지 날짜
		List<String> reserveDate = rpt.reserveDate();
		mav.addObject("date15", reserveDate);
		
		//+15일까지 요일
		mav.addObject("day15", rpt.reserveDay());
		
		//예약한 인원수 map 처리 -key : 날짜+시간, value : 인원
		mav.addObject("countReserve", rpt.countResv(hno, startTime));
		
		//시간/날짜별 예약받을 총 인원수
		mav.addObject("totalReserve", rpt.totalResv(hno, startTime));		
		
		return mav;
	}
	
	
	
	
	
	//예약인원변경요청 ajax
	@RequestMapping("/HopChangeRevNum")
	public void changeNum(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, 
									@Param("selNum") String selNum,
									@Param("date") String date,
									@Param("time") String time,
									@Param("hno") int hno) {

		//현재 시간 10 : 00 형태 -> 10:00:00으로 고치고
		String ftime = time.substring(0, 2)+":00:00";
		
		//날짜 7월 28일 -> 2022-07-28로 고치기...
		String month = date.split("월")[0];	
		if(month.length()==1) {
			month = "0"+month;
		}
		
		//일 처리
		int length = date.length();
		
		String day = ""+Integer.parseInt(date.substring(length-3, length-1).trim());
		if(day.length()==1) {
			day = "0"+day;
		}
		System.out.println("다시 day : " + day);
		
		String fdate = LocalDate.now().getYear()+"-"+month+"-"+day;
		
		//reservationTime 테이블에 있는지 확인
		String rbl = dao.returnBoolean(hno, fdate, ftime);
		
		if(rbl.equals("false")) {
			dao.makeRevTime(hno, fdate, ftime, selNum);
		}else {
			int tno = Integer.parseInt(rbl);
			dao.updateRevTime(selNum, tno);
		}
				
	}
}
