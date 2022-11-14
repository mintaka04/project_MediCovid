package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class hospitalReservationDTO {

	private int hno;
	private String hpw;
	private String hname;
	private String hgu;
	private int hcode;
	private String haddress;
	private String htel;
	private String htime;
	private String hx;
	private String hy;
	private int rno;
	private String rdate;
	private String rtime;
	private String rname;
	private String rtel;
	private String rremark;
	private int rstatus;
	private int uno;
	

}
