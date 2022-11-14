package kr.co.medicovid.security.hjj;


import org.springframework.beans.factory.annotation.Autowired;

//1.코드받기(인증)2.엑세스토큰(권한),
//3.사용자 프로필정보를가져오고,4-1.그정보를통해서 회원가입을 진행시키기도 함
//4-2 (이메일,전화번호,이름,아이디)쇼핑몰->추가적으로(집주소),백화점몰->(vip등급,일반등급)




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.medicovid.security.hjj.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록!
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true) //secured 어노테이션 활성화,preAuthorize postAuthorize 어노테이션 활성화
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	 @Autowired
	 private PrincipalOauth2UserService principalOauth2UserService;
	
	    
	   //해당메서드의 리턴되는 오브젝트를 loc로 등록해준다
	   @Bean
 	    public BCryptPasswordEncoder  encodePwd() {
	    	return new BCryptPasswordEncoder();
	    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
	    http.authorizeRequests()
	        .antMatchers("/user/**").authenticated() //인증만되면 들어갈수있는 주소
	        .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
	        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
	        .anyRequest().permitAll()
	        .and()
	        .formLogin()
	        .loginPage("/loginForm")
	        .loginProcessingUrl("/login")// /login  주소가 호출이되면 시큐리티가 낚아채서 로그인을 진행해준다 컨트롤로에 /로그인을안만들어줘도됨
	        .defaultSuccessUrl("/main")//메인페이지로 이동
	        .and()
	        .oauth2Login()
	        .loginPage("/loginForm")  // oauth2Login 시 loginForm
	        .defaultSuccessUrl("/main")
	        .userInfoEndpoint()
	        .userService(principalOauth2UserService); //구글로그인이 완료된 뒤 후처리가 필요함 Tip 코드x (엑세스토큰+사용자프로필정보 O)
	    
	}

	
}
