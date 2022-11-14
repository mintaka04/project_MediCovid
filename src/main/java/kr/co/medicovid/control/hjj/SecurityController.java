package kr.co.medicovid.control.hjj;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.medicovid.dao.hjj.UserInfoDAO;
import kr.co.medicovid.dto.UsersDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;
import kr.co.medicovid.security.hjj.oauth.FindPwMail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SecurityController {
	@Autowired
	UserInfoDAO udao;
	
	@Autowired
	private PasswordEncoder passwdEncoder;
	
	@Autowired
	FindPwMail findPwMail;

	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) { //authentication DI(의존성 주입)      ,,,, @AuthenticationPrincipal통해 세션정보에 접근할수있음
		System.out.println("test/login ================");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();   //getPrincipal=PrincipalDetails
		System.out.println("authentication:"+principalDetails.getUdto());
		
		System.out.println("userDetails: "+userDetails.getUdto());
		//userDetails 일반 로그인 
		return "세션 정보 확인하기";
	}
	
	 
	


		@GetMapping("/test/oauth/login")
		public @ResponseBody String testOauthLogin(Authentication authentication,
				@AuthenticationPrincipal OAuth2User oauth ) { //authentication DI(의존성 주입)      ,,,, @AuthenticationPrincipal통해 세션정보에 접근할수있음
			System.out.println("test/oauth/login ================");
			OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();   //getPrincipal=PrincipalDetails
			System.out.println("authentication:"+oAuth2User.getAttributes());
			 
			System.out.println("oAuth2User: "+oauth.getAttributes());
			//oAuth2User sns로그인
			return "OAuth 세션 정보 확인하기";
		}


	
	//스프링시큐리티 추가했더니 해당주소를 낚아채버림
	@GetMapping("/loginForm") 
	public String loginForm() {
        //1.전화번호를 입력하면 컨트롤러로 입력받아야함
		
      		
		//2. 입력한전화번호로 아이디 값 가지고와서 jsp파일로 넘겨주기
		
	
		return "loginForm"; 
	}
	
	@PostMapping("/findId")
	@ResponseBody          //파일에 값만보내는것
	public String findId(@RequestBody HashMap<String, Object> map) {
		// object 클래스 int 형변환
		// object > string > int
		String test = udao.getFindId(Integer.parseInt(map.get("utel").toString()));
		if(test ==null) {
			test="no";
		}
		return test;
		
	}
	
	
	//임시비밀번호
	@PostMapping("/findMemberPwd")
	@ResponseBody
	 String findUsersPwd(@RequestParam("uid")String uid) throws Exception {
		System.out.println("uid: "+uid);
		UsersDTO udto = udao.findUsersPwd(uid); //메일이있는지 찾는것
		
		if(udto !=null) {
			// 임시 패스워드 메일 발송 및 변수 저장
			String tempPw = passwdEncoder.encode(findPwMail.sendSimpleMessage(udto.getUid())); //findPwMail.sendSimpleMessage = 메일보내는 메서드
			 System.out.println("tempPw : " + tempPw);
			// 임시 패스워드 db 에 저장
			udao.updatePw(tempPw, udto.getUno()); //임시패스워드 바꾸는 메서드
			
			return "변경완료";
	 
		}
		return null;
		
		
		
		
		
	}
	
	
	

	
	/*
	 * @GetMapping("/user") public String user(@AuthenticationPrincipal
	 * PrincipalDetails principalDetails) {
	 * System.out.println("principalDetails: "+principalDetails.getAttributes());
	 * return "user"; }
	 */

	
	/*
	 * @Secured("ROLE_ADMIN") //간단하게걸고싶으면이렇게
	 * 
	 * @GetMapping("/myinfo") public @ResponseBody String myinfo() { return "개인정보";
	 * }
	 * 
	 * @PreAuthorize("hasRole('ROLE_MANAGER')or hasRole('ROLE_ADMIN')") //data 메서드가
	 * 실행되기 직전에 시전
	 * 
	 * @GetMapping("/data") public @ResponseBody String data() { return "데이터정보"; }
	 */
	

}
