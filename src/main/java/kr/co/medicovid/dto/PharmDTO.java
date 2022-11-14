package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmDTO {
	private String pname;
	private String paddress;
	private String ptel;
	private String ptime;
	private String px;
	private String py;
}
