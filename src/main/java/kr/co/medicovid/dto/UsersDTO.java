package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDTO {
	private int uno;
	private String uid;
	private String upw;
	private String uname;
	private String utel;
	private String uloginType;
	private int penalty;
	private String role;  //ROLE_USER,ROLE_ADMIN
}
