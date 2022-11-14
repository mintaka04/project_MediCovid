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
	
	
	@RequestMapping("/Hospital")
	public ModelAndView hopDetail(@AuthenticationPrincipal PrincipalDetails principalDetails, ModelAndView mav, @RequestParam int hno) throws IOException, ParseException {
		
		//System.out.println("왜안와");
		
		if(principalDetails == null) { // 세션 없을 때 ===> 로그인 안된 경우
			mav.addObject("model", "권한없음");
			//System.out.println("사용자가 아님");
		}else {
			if(principalDetails.getType().equals("user")) {
				mav.addObject("model", principalDetails.getUdto());
				//System.out.println("사용자로 로그인됨");
			}else {
				mav.addObject("model", "권한없음");
				//System.out.println("사용자가 아님");
			}			
		}
		
		
		mav.setViewName("hosDetail/hospitalDetail");
		
		//병원관련정보
		HospitalInfoDTO dto = dao.selectHospital(hno);
		mav.addObject("hospitalInformation", dto);
		
		//병원분류정보
		boolean[] arr = new boolean[3];
		
		for(int a : dao.getCategory(hno)) {
			if(a == 1) {
				arr[0] = true;
				//System.out.println("호흡기");
			}else if(a == 2) {
				arr[1] = true;
				//System.out.println("재택");
			}else if(a==3) {
				arr[2] = true;
				//System.out.println("전화");
			}
		}
		mav.addObject("hospitalCat", arr);
		
		//약국정보
		List<PharmDTO> pharm = ps.pharmList(dto.getHx(), dto.getHy());
		mav.addObject("pharmList", pharm);
		
		
		//+15일까지 날짜
		List<String> reserveDate = rpt.reserveDate();
		mav.addObject("date15", reserveDate);
		
		//+15일까지 요일
		mav.addObject("day15", rpt.reserveDay());
		
		//운영시간간 중 시작시간 string 처리
		List<String> startTime = rpt.startTime(dto.getHtime());
		mav.addObject("startTimes", startTime);
		
		return mav;
	}
	
	@RequestMapping("/pharmMap")
	public String goToMap() {
		return "hosDetail/pharmMap";
	}
	
	
	
	
	
	@RequestMapping("/ajaxRevTime")
	@ResponseBody
	public JSONObject getPossibleRev(HttpServletResponse response, @RequestParam String selectedDate, @RequestParam String htime, @RequestParam int hno) {
		
		JSONObject result = new JSONObject();
		
		System.out.println("받아온 json input값 : " + selectedDate);
		System.out.println("컨트롤러 운영시간 :"+htime);
		result = rpt.getRevTime(selectedDate, htime, hno);
		
		//System.out.println("json값? : " + result.get("posRvNum").toString());
				
		return result;
	}
	
	
	
	//예약 등록 ajax
	@RequestMapping("/MakeReservation")
	public void visitConfirm(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletResponse response, 
				@RequestParam String rdate, @RequestParam String rtime,
				@RequestParam String rname, @RequestParam String rtel,
				@RequestParam String rremark, @RequestParam int hno) {
		
		//rtime 11 : 00 -> 11:00:00
		rtime = rtime.substring(0,2)+":00:00";
		System.out.println("rtime : " + rtime);
		
		
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
		System.out.println("rdate : " + rdate);
		
		int uno = principalDetails.getUdto().getUno();
		
		dao.makeReserv(rdate, rtime, rname, rtel, rremark, uno, hno);
	}
	
}
