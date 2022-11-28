package kr.co.medicovid.control.hgh;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.hgh.HosDetailDAO;
import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.PharmDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;
import kr.co.medicovid.service.hgh.PharmService;
import kr.co.medicovid.service.hgh.RevPerTimeService;

@Controller
public class HospitalDetailController {
	
	@Autowired
	HosDetailDAO dao;
	
	@Resource
	PharmService ps;
	
	@Resource
	RevPerTimeService rpt;
	
	
	
	/*
	 * 병원 디테일 페이지
	 * url 예시 : /Hospital?hno=병원번호
	 * 
	 * 전달되는 데이터 : 
	 * 로그인 상태 관련 세션, 병원 상세 정보, 병원 카테고리 정보, 근처 약국 정보,
	 * +15일까지의 날짜와 요일 정보, 예약 가능 시간 리스트
	 * 
	 */
	@RequestMapping("/Hospital")
	public ModelAndView hopDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, ModelAndView mav, @RequestParam int hno) throws IOException, ParseException {
		
		
		//로그인 된 상태 && 사용자가 일반회원일 경우에만 세션 정보 데이터 추가.
		if(principalDetails == null) {
			//로그인되지 않음.
			mav.addObject("model", "권한없음");
		}else {
			//로그인되었고 일반회원
			if(principalDetails.getType().equals("user")) {
				mav.addObject("model", principalDetails.getUdto());
			}else {
				//로그인되었지만 일반회원이 아님( ex: 관리자)
				mav.addObject("model", "권한없음");
			}			
		}
			
		
		//병원관련정보 데이터 추가
		HospitalInfoDTO dto = dao.selectHospital(hno);
		mav.addObject("hospitalInformation", dto);
		
		
		//병원카테고리정보 데이터 추가
		boolean[] arr = new boolean[3];
		
		for(int a : dao.getCategory(hno)) {
			if(a == 1) {
				//호흡기관련 병원 분류
				arr[0] = true;
			}else if(a == 2) {
				//재택 관련 병원 분류
				arr[1] = true;
			}else if(a==3) {
				//전화상담 관련 병원 분류
				arr[2] = true;
			}
		}
		mav.addObject("hospitalCat", arr);
		
		
		//근처 약국 정보 데이터 추가
		List<PharmDTO> pharm = ps.pharmList(dto.getHx(), dto.getHy());
		mav.addObject("pharmList", pharm);
		
		
		//x월 x일 형태인 +15일까지 날짜(주말제외) 데이터 추가
		List<String> reserveDate = rpt.reserveDate();
		mav.addObject("date15", reserveDate);
		
		
		//x요일 형태인 +15일까지 요일(주말제외) 데이터 추가
		mav.addObject("day15", rpt.reserveDay());
		
		
		//운영 시간 범위 내의 예약가능시간 리스트 데이터 추가
		List<String> startTime = rpt.startTime(dto.getHtime());
		mav.addObject("startTimes", startTime);

		
		mav.setViewName("hosDetail/hospitalDetail");
		
		return mav;
	}
	
	
	
	/*
	 * 해당 약국 지도를 새창으로 띄워줌.
	 * url : /pharmMap?py=위치좌표&py=위치좌표
	 */
	@RequestMapping("/pharmMap")
	public String goToMap() {
		return "hosDetail/pharmMap";
	}
	
	
	
	/*
	 * 예약 진행 모달 창에서 원하는 예약 날짜 선택시
	 * 해당 날짜의 시간별 예약 가능 인원수를 받아옴
	 */
	@RequestMapping("/ajaxRevTime")
	@ResponseBody
	public JSONObject getPossibleRev(HttpServletResponse response, @RequestParam String selectedDate, @RequestParam String htime, @RequestParam int hno) {
		
		JSONObject result = new JSONObject();
		
		//System.out.println("받아온 json input값 : " + selectedDate);
		//System.out.println("컨트롤러 운영시간 :"+htime);
		result = rpt.getRevTime(selectedDate, htime, hno);
		
		//System.out.println("json값? : " + result.get("posRvNum").toString());
				
		return result;
	}
	
	
	
	/*
	 * 예약 정보 db에 등록
	 */
	@RequestMapping("/MakeReservation")
	public void visitConfirm(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, 
				@RequestParam String rdate, @RequestParam String rtime,
				@RequestParam String rname, @RequestParam String rtel,
				@RequestParam String rremark, @RequestParam int hno) {
		
		//rtime 11 : 00 -> 11:00:00
		rtime = rtime.substring(0,2)+":00:00";
		//System.out.println("rtime : " + rtime);		
		
		
		//rdate 8월 1일 -> 2022-08-11
		String month = rdate.split("월")[0];	
		System.out.println(month);
		if(month.length()==1) {
			month = "0"+month;
		}
		int length = rdate.length();
		String day = ""+Integer.parseInt(rdate.split("월")[0].split("일")[0].trim());
		System.out.println(day);
		if(day.length()==1) {
			day = "0"+day;
		}	
		rdate = LocalDate.now().getYear()+"-"+month+"-"+day;
		//System.out.println("rdate : " + rdate);
		
		
		//세션에 저장된 회원번호
		int uno = principalDetails.getUdto().getUno();
		
		dao.makeReserv(rdate, rtime, rname, rtel, rremark, uno, hno);
	}
	
}
