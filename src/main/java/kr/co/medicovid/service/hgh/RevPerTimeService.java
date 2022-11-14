package kr.co.medicovid.service.hgh;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.medicovid.dao.hgh.HosAdminDAO;

@Service
public class RevPerTimeService {

	@Autowired
	HosAdminDAO dao;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	
	// 주말 제외, 주일로만 +15일되는 일자들리턴.
	public List<String> reserveDate() {

		List<String> ldl = new ArrayList<>();

		// 15일 후 날짜까지 구하기.
		for (int i = 0; i <= 15; i++) {

			LocalDate after = LocalDate.now().plusDays(i);

			// 해당 날짜의 요일구하기
			String day = after.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

			// 날짜 중 주말제외
			if (!day.equals("토요일") && !day.equals("일요일")) {
				// System.out.println(day);
				DateTimeFormatter df = DateTimeFormatter.ofPattern("M월 d일");
				ldl.add(after.format(df));
			}
		}

		return ldl;
	}

	
	
	
	
	
	// reserveDate에 해당되는 날짜의 요일리턴.
	public List<String> reserveDay() {

		List<String> ldd = new ArrayList<>();

		// 오늘날짜
		LocalDate now = LocalDate.now();

		// 15일 후 날짜까지 구하기.
		for (int i = 0; i <= 15; i++) {

			LocalDate after = LocalDate.now().plusDays(i);

			// 해당 날짜의 요일구하기
			String day = after.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);

			// 날짜 중 주말제외
			if (!day.equals("토요일") && !day.equals("일요일")) {
				// System.out.println(day);
				ldd.add(day);
			}
		}

		return ldd;
	}

	
	
	
	
	
	// 운영시간에 해당하는 시간중 시작시간만 string으로 변환
	public List<String> startTime(String htime) {

		List<String> result = new ArrayList<>();

		result.add(htime.substring(0, 2) + " : " + htime.substring(2, 4));

		int starttime = Integer.parseInt(htime.substring(0, 2));
		int endtime = Integer.parseInt(htime.substring(4, 6));

		for (int i = starttime + 1; i <= endtime; i++) {
			if(i==endtime) {
				if(htime.substring(6).equals("30")) {
					if (i < 10) {
						result.add("0" + i + " : " + "00");
					} else {
						result.add(i + " : " + "00");
					}	
				}
			}else {
				if (i < 10) {
					result.add("0" + i + " : " + "00");
				} else {
					result.add(i + " : " + "00");
				}				
			}
			
		}

		return result;
	}

	
	
	
	
	
	
	
	// 운영시간 중 끝시간
	public List<String> endTime(String htime) {

		List<String> result = new ArrayList<>();

		int starttime = Integer.parseInt(htime.substring(0, 2));
		int endtime = Integer.parseInt(htime.substring(4, 6));

		for (int i = starttime + 1; i <= endtime; i++) {

			if (i < 10) {
				result.add("0" + i + " : " + "00");
			} else {
				result.add(i + " : " + "00");
			}

		}
		
		if(htime.substring(6).equals("30")) {
			result.add(htime.substring(4,6)+" : "+htime.substring(6));
		}

		return result;
	}
	
	
	
	
	//예약한 인원 수 가져오기-key : 날짜+시간, value : 인원
	public Map<String, Integer> countResv(int hno, List<String> startTime){
		//startTime은 09 : 00 형태
		//reserveDate는 m월 d일 형태...
		
		
		Map<String, Integer> result = new HashMap<>();
		
		List<LocalDate> reserveDate = new ArrayList<>();
		// 15일 후 날짜까지 구하기.
		for (int i = 0; i <= 15; i++) {
			LocalDate after = LocalDate.now().plusDays(i);
	
			// 해당 날짜의 요일구하기
			String day = after.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
	
			// 날짜 중 주말제외
			if (!day.equals("토요일") && !day.equals("일요일")) {				
				reserveDate.add(after);
			}
		}
		
		
		for(LocalDate rdate : reserveDate) {
			for(String time : startTime) {
				
				//예약한 인원수 가져오기...
				//rdate는 2022-02-02형태, date는 0월 0일 형태
				DateTimeFormatter df = DateTimeFormatter.ofPattern("M월 d일");
				String date = rdate.format(df);
				
				//rtime은 09:00:00형태, time은 09:30 형태. 
				String rtime = time.substring(0,2)+":00:00";
				
				int num = dao.dateTimeResvNum(hno, rdate.toString(), rtime);
				//System.out.println(date+time + ", " + num);
				//key값이 7월 25일09 : 00 형태로 들어가야하는데.
				result.put(date+time, num);
			}			
		}
		
		
		return result;
	}
	
	
	
	
	
	
	//시간/날짜별 예약받을 총 인원수 -> 위 메서드랑 int num 주는 것만 다름-->나중에 합치기
	public Map<String, Integer> totalResv(int hno, List<String> startTime){
		
		Map<String, Integer> result = new HashMap<>();
		
		List<LocalDate> reserveDate = new ArrayList<>();
		// 15일 후 날짜까지 구하기.
		for (int i = 0; i <= 15; i++) {
			LocalDate after = LocalDate.now().plusDays(i);
	
			// 해당 날짜의 요일구하기
			String day = after.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
	
			// 날짜 중 주말제외
			if (!day.equals("토요일") && !day.equals("일요일")) {				
				reserveDate.add(after);
			}
		}
		
		
		for(LocalDate rdate : reserveDate) {
			for(String time : startTime) {
				
				//예약한 인원수 가져오기...
				//rdate는 2022-02-02형태, date는 0월 0일 형태
				DateTimeFormatter df = DateTimeFormatter.ofPattern("M월 d일");
				String date = rdate.format(df);
				
				//rtime은 09:00:00형태, time은 09:30 형태. 
				String rtime = time.substring(0,2)+":00:00";
				
				int num = dao.dateTimeResvTotalNum(hno, rdate.toString(), rtime);
				//System.out.println(date+time + ", " + num);
				//key값이 7월 25일09 : 00 형태로 들어가야하는데.
				result.put(date+time, num);
			}			
		}
		
		
		return result;
		
	}
	
	
	
	
	public String checkpw(String pw, int hno) {
		
		String result;
		
		String basicPw = dao.getPW(hno);
		boolean matchresult = encoder.matches(pw, basicPw);
		System.out.println("비밀번호 일치 여부 : " + matchresult);
		
		if(matchresult) {
			result = "true";
		}else {
			result = "false";
		}
			
		return result;
	}
	
	
	
	
	//예약 가능한 날짜 만들어줌
	public JSONObject getRevTime(String date, String htime, int hno) {
		
		//System.out.println("서비스에 들어옴 : " + date);		
		
		//1. 8월 4일 형태를 -> 2022-08-04형태로 고쳐주기
		String month = date.split("월")[0];	
		if(month.length()==1) {
			month = "0"+month;
		}
		int length = date.length();
		String day = ""+Integer.parseInt(date.substring(length-3, length-1).trim());
		if(day.length()==1) {
			day = "0"+day;
		}	
		String fdate = LocalDate.now().getYear()+"-"+month+"-"+day;
		//System.out.println("fdate : " + fdate);
		
		//2. 해당 병원, 해당 시간/날짜 예약 가능한 시작시간에 따른 예약 가능한 인원수 가져오기.
		List<Integer> revMem = new ArrayList<>();
		int st = Integer.parseInt(htime.substring(0,2));
		int et = Integer.parseInt(htime.substring(4,6));
		
		for(int i = st; i <= et; i++) {
			String timet = Integer.toString(i);
			if(timet.length() == 1) {
				int possibleResv = dao.dateTimeResvTotalNum(hno, fdate, "0"+timet+":00:00")-dao.dateTimeResvNum(hno, fdate, "0"+timet+":00:00");
				revMem.add(possibleResv);
				//System.out.println(timet+"시 예약가능 인원수 : " + possibleResv);
			}else {
				int possibleResv = dao.dateTimeResvTotalNum(hno, fdate, timet+":00:00")-dao.dateTimeResvNum(hno, fdate, timet+":00:00");
				revMem.add(possibleResv);
				//System.out.println(timet+"시 예약가능 인원수 : " + possibleResv);
			}
		}
		
		
		//json으로 만들어주기
		JSONObject result = new JSONObject();
		result.put("posRvNum", revMem);
		
		return result;		
	}

}
