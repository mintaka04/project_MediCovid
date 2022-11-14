package kr.co.medicovid.security.hjj.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.medicovid.dao.hjj.UserInfoDAO;
import kr.co.medicovid.dto.UsersDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;
import kr.co.medicovid.security.hjj.oauth.provider.KakaoUserinfo;
import kr.co.medicovid.security.hjj.oauth.provider.NaverUserinfo;
import kr.co.medicovid.security.hjj.oauth.provider.SocialLogin;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	
	//1.(sns로그인시)loadUser실행 =>userRequest얻어옴 =>
	
	
	
	@Autowired
	UserInfoDAO udao;
	

	
	
	//구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:"+userRequest.getClientRegistration()); //ClientRegistration로 어떤 OAuth로 로그인했는지 정보확인가능
		System.out.println("getAccessToken:"+userRequest.getAccessToken().getTokenValue()); //AccessToken 토큰 요청
	
		
		OAuth2User oauth2User = super.loadUser(userRequest); //userRequest 를 loadUser를 실행해서 유저정보가 oauth2User로 변환
		//구글로그인버튼클릭 => 구글로그인창 =>로그인을 완료 => code를 리턴(OAuth-Client라이브러리)=> AccessToken 요청
		//userRequset 정보 -> lodUser함수 => 구글로부터 회원프로필 받아준다.
		System.out.println("userRequest: "+userRequest);
		System.out.println("oauth2User: "+oauth2User);
		System.out.println("getAttributes:"+oauth2User.getAttributes());
		
	
		
		
		 System.out.println("oauth2User: "+oauth2User);
	   
	    
		
		return oAuth2UserLogin(userRequest,oauth2User);
	}




	private OAuth2User oAuth2UserLogin(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
		
		SocialLogin loginUser = null;   //인터페이스 SocialLogin
		
		// uloginType통해서 어떤 sns로 로그인했는지알수있음
		String uloginType =userRequest.getClientRegistration().getRegistrationId();
		
		
		//sns어디껀지 판단
		if(uloginType.equals("naver")) {
			loginUser = new NaverUserinfo(oauth2User.getAttributes());
		}else if(uloginType.equals("kakao")) {
			loginUser = new KakaoUserinfo(oauth2User.getAttributes());
		}	
		
		//sns로그인했을떄 어떤걸로그인했던간에 이메일정보만 튀어나옴
		String memail = loginUser.getEmail();
		
		UsersDTO udto = udao.getSnsOne(memail, uloginType);
		
		
		if(udto==null) {
			System.out.println("sns로그인 최초");
		  udto = udto.builder()
				.uid(memail)
				.uname(loginUser.getName())
				.uloginType(uloginType)
				.build();
		  //bulid 된 정보로 sns 회원가입
		  udao.insertSNS(udto);
		  
		}else {
			System.out.println("이미 아이디가있는 사용자입니다");
		}
			
	  	
	    //인서트후 sns유저같은경우 로그인으로 바로넘어가기떄문에 로그인창으로안보낸상태에서 mdto를 가져와야함 
		udto =udao.getSnsOne(memail, uloginType);
		
		
		
		return new PrincipalDetails(udto,oauth2User.getAttributes(), "user");
	}
}
 
      

//{sub=114284699015909773310, name=BEE BEE, given_name=BEE,
//family_name=BEE, picture=https://lh3.googleusercontent.com/a/AItbvmmfXN7kc4hhTVLQ3achDcRqXez6MU3w7KdYKdyD=s96-c, 
//email=hothott.jjh@gmail.com, email_verified=true, locale=ko}

//uname = "google_114284699015909773310"
//password = "암호화(겟인데어)" 아무거나해도됨 널만아니면
//email = "hothott.jjh@gmail.com"
//role = "ROLE_USER""