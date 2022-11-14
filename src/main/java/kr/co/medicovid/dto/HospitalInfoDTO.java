package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalInfoDTO {
	private int hno;
	private String hpw;
	private String hname;
	private String hgu;
	private float hcode;
	private String haddress;
	private String htel;
	private String htime;
	private String hx;
	private String hy;
	private int hrevptime;
	private int hds;
	private int hns;
	private int hfs;
	private int hts;
}