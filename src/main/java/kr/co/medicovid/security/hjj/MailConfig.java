package kr.co.medicovid.security.hjj;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("${mailconfig.username}")
	String username;
	
	@Value("${mailconfig.pw}")
	String pw;
	
	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.naver.com"); // 메인 도메인 서버 주소
		javaMailSender.setUsername(username);  //네이버 smtp 설정이메일
		javaMailSender.setPassword(pw); //설정이메일 비번
		javaMailSender.setPort(465);
		javaMailSender.setJavaMailProperties(getMailProperties()); //메일인증서버가져오기
		
		return javaMailSender;
		
	}

	private Properties getMailProperties() {
		Properties properties = new Properties();
	    properties.setProperty("mail.transport.protocol","smtp");// 프로토콜 설정
	    properties.setProperty("mail.smtp.auth", "true");// smtp 인증
	    properties.setProperty("mail.smtp.starttls.enable","true");// smtp strattles 사용
	    properties.setProperty("mail.debug", "true");// 디버그 사용
	    properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com");// ssl 인증 서버는 smtp.naver.com
	    properties.setProperty("mail.smtp.ssl.enable", "true");// ssl 사용
	    
		
		return properties;
	}
}
