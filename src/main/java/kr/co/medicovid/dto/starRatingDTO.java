package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class starRatingDTO {
	private int sno;
	private int sds;
	private int sns;
	private int sfs;
	private int sts;
	private int rno;
	private int hno;
}
