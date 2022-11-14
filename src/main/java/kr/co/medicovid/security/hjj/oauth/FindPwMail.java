package kr.co.medicovid.security.hjj.oauth;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;



@Service
public class FindPwMail {

	@Autowired
	JavaMailSender emailsender;
	
	// 임시 패스워드
	private final String tempPW = createKey();

	// 매일내용작성하는곳
	public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {//받는사람 이메일 String to
		MimeMessage message = emailsender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject(" medicovid 계정 임시 패스워드");// 제목
		String msgg = "";
		msgg += "<h1>안녕하세요 medicovid 입니다</h1>";
		msgg += "<p>회원님 임시비밀번호 입니다</p>";
		msgg += "<br>";
		msgg += "<h3>임시비밀번호</h3>";
		msgg += "임시비밀번호 : <strong>";
		msgg += tempPW + "</strong>";  //tempPW => 생성되는인증코드 for(int i = 0; i<8; i++) {//인증코드 8자리값을 나타냄
		msgg += "<br>";
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("svace7@naver.com", "Medicovid_Admin"));// 보내는 사람이메일,  보내는사람이름

		return message;
	}
	
	
	
	//임시비밀번호 랜덤값생성하는곳
	private String createKey() {
		 StringBuffer key = new StringBuffer();
		 Random rnd =new Random();
		 for(int i = 0; i<8; i++) {//인증코드 8자리
			 int index =rnd.nextInt(3);//0~2까지 랜덤
			 switch(index) {
			 case 0:
				 key.append((char) ((int) (rnd.nextInt(26)) + 97));
					break;
				case 1:
					key.append((char) ((int) (rnd.nextInt(26)) + 65));
					// A~Z
					break;
				case 2:
					key.append((rnd.nextInt(10)));
					// 0~9
					break;
			 }
			 
		 }
		return key.toString();
	}
	
	
	// 이메일 발송되는곳
		
		public String sendSimpleMessage(String to) throws Exception {//여기서 String to 받는사람 이메일
			// TODO Auto-generated method stub
			MimeMessage message = createMessage(to); //to =받는사람 이메일
			try {// 예외처리
				emailsender.send(message); //이순간에 메일이가짐
			} catch (MailException es) {
				es.printStackTrace();
				throw new IllegalArgumentException();
			}
			return tempPW; //String 리턴이되는건 랜던값
		}
	
}
