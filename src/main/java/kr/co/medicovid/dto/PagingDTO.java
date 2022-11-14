package kr.co.medicovid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingDTO {
	private int currentPage;
	private int startNo;
	private int countPerPage;
	private int totalPage;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
}
