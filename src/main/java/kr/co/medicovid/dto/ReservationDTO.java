package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
	private int rno;
	private String rdate;
	private String rtime;
	private String rname;
	private String rtel;
	private String rremark;
	private int rstatus;
	private int uno;
	private int hno;
}
