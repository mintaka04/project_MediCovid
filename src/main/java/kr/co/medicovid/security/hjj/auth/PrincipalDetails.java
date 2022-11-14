package kr.co.medicovid.security.hjj.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.dto.UsersDTO;
import lombok.Data;

//시큐리티가 /login  주소 요청이 오면 낚아채서 로그인을 진행시킴
//로그인을 진행이 완료가되면 시큐리티 session을 만들어줍니다 (Security ContexHolder)
//오브젝트타입 ==>Authentication 타입객체
//Authentication 안에 User정보가 있어야 됨.
//User오브젝트 타입 =>UserDetails 타입 객체


// type : 일반유저, 병원 구분 => user, hosp


@Data
public class PrincipalDetails implements UserDetails,OAuth2User{
	
	private UsersDTO udto;
	private HospitalInfoDTO hdto;
	private Map<String,Object>attributes;
	
	private String type;
	
	// 일반로그인할떄사용하는생성자
	public PrincipalDetails(UsersDTO udto, String type) {
		this.udto=udto;
		this.type = type;
	}
	
	// 병원 로그인
	public PrincipalDetails(HospitalInfoDTO hdto, String type) {
		this.hdto = hdto;
		this.type = type;
	}
	
	//OAuth 로그인할떄사용는생성자
	public PrincipalDetails(UsersDTO udto,Map<String,Object>attributes, String type) {
		this.udto=udto;
		this.attributes=attributes;
		this.type = type;
	}
	
	
	//해당 User의 권한을 리턴하는곳!!@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				if(type.equals("user")) {
					return udto.getRole();

				}
				return "ROLE_MANAGER";
			}
		});
		return collect;
	}

	
	//패스워드리턴 
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		if(type.equals("user")) {
			return udto.getUpw();

		}else {
			return hdto.getHpw();
		}
	}

	//유저네임
	@Override
	public String getUsername() {
		if(type.equals("user")){
			return udto.getUname();
		}else {
			return hdto.getHname();
		}
	}
 
	//개정만료??
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	
	//개정잠겻니?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 너무오래사용한거아니니?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}



	@Override
	public Map<String, Object> getAttributes() {
		
		return attributes;
	}



	@Override
	public String getName() {
		
		return null;
	}
	

}