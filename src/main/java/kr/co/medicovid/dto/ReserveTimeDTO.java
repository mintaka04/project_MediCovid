package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveTimeDTO {
	private int tno;
	private String ttime;
	private int tpeople;
	private int hno;
	private String tdate;
}
