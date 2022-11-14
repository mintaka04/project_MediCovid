package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
	private int chatno;
	private String sender;
	private String receiver;
	private String content;
	private String chatDate;
	private String chatTime;
	private int status;
}
