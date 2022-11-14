package kr.co.medicovid.control.kjh;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.kjh.MyinfoDAO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;

@Controller
public class MyinfoController {
	@Autowired
	MyinfoDAO dao;	
	
	// 마이페이지(진행중인 예약) 가기
	@RequestMapping("/mypage")
	public String myinfo(@AuthenticationPrincipal PrincipalDetails principalDetails, 
			Model model) { //, @RequestParam("hno")int hno // @RequestParam("uno")int uno
//		System.out.println("uno: " +uno);
//		System.out.println("hno: " +hno);
		if(principalDetails == null) { // 세션 없을 때 ===> 로그인 안된 경우
    		return "redirect:loginForm";
    	}else {
        	if(principalDetails.getType().equals("user")) {
        		model.addAttribute("model", principalDetails.getUdto());
        		int uno = principalDetails.getUdto().getUno();
        		model.addAttribute("uno", uno);
        		model.addAttribute("hospital", dao.hospitalIngInfoBringtwo(uno));
        		int length = Integer.parseInt(dao.selecthospitalIngNumInfo(uno));
        		System.out.println(length);
        		
        		if(length >= 3) {
					model.addAttribute("listlength", dao.hospitalIngInfoBringtwo(uno));
					System.out.println("길이가 3보다 큼");
				}else {
					model.addAttribute("listlength", "false");
					System.out.println("길이 3보다 작음");
				}
        	}else if(principalDetails.getType().equals("hosp")) {
        		return "redirect:main";
        	}
    	}
		
//		model.addAttribute("hospital", dao.selecthospitalInfo(hno));
		
		return "myinfo";
	}
	
	// 마이페이지(지난 예약 내역) 가기
	@RequestMapping("/passreservation")
	public String passreservation(@AuthenticationPrincipal PrincipalDetails principalDetails, 
			Model model) {
//		System.out.println("uno: " +uno);
		if(principalDetails == null) { // 세션 없을 때 ===> 로그인 안된 경우
			return "redirect:loginForm";
		}else {
			if(principalDetails.getType().equals("user")) {
				model.addAttribute("model", principalDetails.getUdto());
				int uno = principalDetails.getUdto().getUno();
				model.addAttribute("hospital", dao.hospitalpassedInfoBringtwo(uno));
				
				int length = Integer.parseInt(dao.selecthospitalpassedNumInfo(uno));
				System.out.println(length);
				if(length >= 3) {
					model.addAttribute("listlength", dao.hospitalpassedInfoBringtwo(uno));
					System.out.println("길이가 3보다 큼");
				}else {
					model.addAttribute("listlength", "false");
					System.out.println("길이 3보다 작음");
				}
				
				
			}else if(principalDetails.getType().equals("hosp")) {
				return "redirect:main";
			}
		}
		
		return "passreservation";
	}
	
	// 진행중인 예약 => 예약 취소
	@RequestMapping("/ajaxreservationcancle")
	@ResponseBody
	public String canclereservation(HttpServletResponse response, @RequestParam int rno) {
		System.out.println("ajax : " + rno);
		dao.cancle(rno);
		
		return "ajaxcomplete";
	}
	
	// 진행중인 예약 더 보기 버튼
	@RequestMapping("/ajaxmoresee")
	@ResponseBody
	public ModelAndView ajaxseeplus2(HttpServletResponse response, ModelAndView mav, @RequestParam int uno) {
		System.out.println("ingmoresee");
		mav.setViewName("seemoreajaxingres");
		mav.addObject("hospitallist", dao.selecthospitalInfo(uno));
		return mav;
	}
	
	// 지난 예약 내역 => 더 보기 버튼
	@RequestMapping("/ajaxmoreseepassed")
	@ResponseBody
	public ModelAndView ajaxseeplus(HttpServletResponse response, ModelAndView mav, @RequestParam int uno) {
		System.out.println("passedmoresee");
		mav.setViewName("seemoreajaxpassed");
		mav.addObject("hospitallist", dao.selecthospitalpassedInfo(uno));
		return mav;
	}
	
	
//	@RequestMapping("/passreservation")
//	public String passreservation() {
//		return "passreservation";
//	}
	
}
