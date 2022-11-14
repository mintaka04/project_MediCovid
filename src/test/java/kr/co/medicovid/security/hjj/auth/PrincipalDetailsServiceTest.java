package kr.co.medicovid.security.hjj.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.medicovid.dao.hjj.HospDAO;

@SpringBootTest
class PrincipalDetailsServiceTest {

	@Autowired
	HospDAO hdao;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	PrincipalDetailsService pds;
	
	@Test
	void testLoadUserByUsername() {
		
		
		PrincipalDetails ps = (PrincipalDetails) pds.loadUserByUsername("충정로연세내과의원"+" hosp");
		System.out.println("ps : "+ps);
	}
	
	
	@Test
	void password() {
		String pw = passwordEncoder.encode("0000");
		System.out.println("pw : "+pw);
	}

}
