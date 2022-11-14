package kr.co.medicovid.security.hjj.oauth.provider;

import java.util.Map;

public class KakaoUserinfo implements SocialLogin{

	private Map<String,Object> attributes;
	
	
		public KakaoUserinfo(Map<String, Object> attributes) {
		this.attributes=attributes;
	}

	@Override
	public String uloginType() {
		
		return "카카오";
	}

	@Override
	public String getEmail() {
		Map<String,Object> account = (Map<String,Object>) attributes.get("kakao_account");
		String memail =(String) account.get("email");
		return memail;
	}

	@Override
	public String getName() {
		Map<String,Object> properties = (Map<String,Object>) attributes.get("properties");
		String mname =(String) properties.get("nickname");
		return mname;
	}

}
