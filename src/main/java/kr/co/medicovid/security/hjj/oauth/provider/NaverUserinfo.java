package kr.co.medicovid.security.hjj.oauth.provider;

import java.util.Map;

public class NaverUserinfo  implements SocialLogin{ //NaverUserinfo = SocialLogin로그인구현체

	
	private Map<String, Object> attributes;   //attributes는 =>oauth2User.getAttributes()이걸말함
	
	public NaverUserinfo(Map<String, Object> attributes) {
		this.attributes=attributes;
	}
	
	@Override
	public String uloginType() {
		
		return "naver";
	}

	@Override
	public String getEmail() {
		Map<String,Object> map = (Map<String,Object>) attributes.get("response");
//가져오기     key,value                                           map.get(key)
		return (String) map.get("email"); 
		//map 안에있는값을꺼내와서 string으로형변환해줌
	}

	@Override
	public String getName() {
		Map<String,Object> map = (Map<String,Object>) attributes.get("response");
		return (String) map.get("name");
		//map 안에있는값을꺼내와서 string으로형변환해줌
	}

}
