package kr.co.medicovid.control.hjj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.medicovid.dao.hjj.UserInfoDAO;
import kr.co.medicovid.dto.UsersDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;

@Controller  
public class RegisterController {
	
	@Autowired
	UserInfoDAO dao;
	
	// springSecurity 에서 로그인 할 때 사용하는 패스워드 암호화
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder; 
	         
	
	//회원가입폼
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	//회원가입하기
    @PostMapping("/register")	
    public String registerOk(@ModelAttribute("dto") UsersDTO dto) {
    	System.out.println("변경 전 : "+dto);
    
    	// 회원가입할때 가져온 upw 를 인코딩하는 작업
    	String upwEn = bCryptPasswordEncoder.encode(dto.getUpw());
    	
    	// 인코딩 X 비밀번호 -> 인코딩 비밀번호로 변경 => setter 이용
    	dto.setUpw(upwEn);
    	System.out.println("enpw : "+upwEn);
    	dao.insert(dto);
    	//System.out.println("변경 후 : "+dto);
    	
    	return "redirect:loginForm";
    	
    }
    
    
    
    //중복확인 ajax
    @ResponseBody  //바디내용만 바꿔치기 
    @PostMapping("/uidCheckid")
    public int uidCheckid(@RequestParam("uid")String uid) {
     //1.web에서 이메일을 넘겨받는다
     //2.dao 해당이메일있는지 확인
     //3.이메일이있는경우	1 ,0
     //4.dao결과 리턴
     //5.dao결과에 맞게 뷰에서 보여준다
    	if(uid.equals(dao.select(uid))) { // uid랑 dto객체한개랑 
//    		System.out.println(dao.select(uid)); 
//    		System.out.println("다오실행");
    		return 1; //있으면
    	}
//    	System.out.println("다오실행안됨");
//    	System.out.println("dao: "+dao);
//    	System.out.println("select: "+ dao.select(uid));
    	return 0; 

   
    }
    
    @GetMapping("sessionTest")
    public String session(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
    	
    	
    	
    	if(principalDetails == null) { // 세션 없을 때 ===> 로그인 안된 경우
    		return "redirect:loginForm";
    	}else { // 세션 있을 때 ==> 로그인 된 경우
        	if(principalDetails.getType().equals("user")) {
            	model.addAttribute("model", principalDetails.getUdto());

        	}else if(principalDetails.getType().equals("hosp")) {
        		model.addAttribute("model", principalDetails.getHdto());
        	}
        	return "sessionTest";
    	}

    }
}
