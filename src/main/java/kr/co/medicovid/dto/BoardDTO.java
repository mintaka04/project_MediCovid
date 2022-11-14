package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String regdate;
	private int uno;
}