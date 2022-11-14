package kr.co.medicovid.security.hjj.auth;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.medicovid.dao.hjj.HospDAO;
import kr.co.medicovid.dao.hjj.UserInfoDAO;
import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.UsersDTO;


//시큐리티 설정에서  loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어 있는 loadUserByUsername 함수가 실행 규칙임

@Service
public class PrincipalDetailsService implements UserDetailsService{

	 @Autowired
	 private  UserInfoDAO udao;
	
	 @Autowired
	 private HospDAO hdao;
	 
	 //시큐리티 session(내부 Authentication(내부에 UserDetails))
	 //                Authentication(리턴된값이PrincipalDetails들어감)      
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username : "+username);
		
		StringTokenizer st = new StringTokenizer(username);
		String userid = st.nextToken();
		String type = st.nextToken();
		
		if(type.equals("user")) {
			
			// username 으로 유저 가져오기
			 UsersDTO udto = udao.selectOne(userid);
			 
			 return new PrincipalDetails(udto, type);
		}else if(type.equals("hosp")) {
			HospitalInfoDTO hdto = hdao.loginHosp(userid);
			
			return new PrincipalDetails(hdto, type);
		}
		
		
		
		 
		   
//		  System.out.println("username:"+username);
//		  System.out.println("udto:"+udto);
//		 if(udto != null) {
//			 //udto 가 null 이 아니면 유저가있는것
//			 return new PrincipalDetails(udto);
//		 }
		return null;	
	}

}
