package kr.co.medicovid.security.hjj;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SecurityConfigTest {

	@Autowired
	BCryptPasswordEncoder encoder;
	
	
	@Test
	void testEncodePwd() {
		boolean result = encoder.matches("qwer!1234", "$2a$10$2rRhYU0P/onuFwY2jVUcZ.oXJ7CCzmG2Kk6u.YcExFQXhUnTW2dM2");
		System.out.println("result : "+result);
	}

}
