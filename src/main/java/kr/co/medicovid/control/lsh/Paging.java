package kr.co.medicovid.control.lsh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.medicovid.dao.lsh.AdminDAO;
import kr.co.medicovid.dao.lsh.BoardDAO;
import kr.co.medicovid.dao.lsh.SearchResultDAO;
import kr.co.medicovid.dto.PagingDTO;

@Component
public class Paging {
	@Autowired
	AdminDAO dao;

	@Autowired
	BoardDAO bdao;

	@Autowired
	SearchResultDAO srdao;

	// 병원 페이징
	public PagingDTO hospitalPaging(int currentPage) {

		int totalCount = dao.getTotalHospitalCount();

		return paging(totalCount, currentPage, 10);
	}

	// 사용자 페이징
	public PagingDTO userPaging(int currentPage) {

		int totalCount = dao.getTotalUserCount();

		return paging(totalCount, currentPage, 10);
	}

	// 사이디 아이디 검색 후 페이징
	public PagingDTO userSearchPaging(int currentPage, String uid) {
		int totalCount = dao.getUserCountByUid(uid);

		return paging(totalCount, currentPage, 10);
	}

	// 공지사항 페이징
	public PagingDTO boardPaging(int currentPage) {
		int totalCount = bdao.getTotalCount();

		return paging(totalCount, currentPage, 10);
	}

	// 검색 페이징
	public PagingDTO searchPaging(int currentPage, String keyword, String[] guArr, int category) {
		int totalCount = srdao.getTotalCount(category, keyword, guArr);
		// System.out.println("category : " + category);
		// System.out.println("keyword : " + keyword);
		// System.out.println("guArr.length : " + guArr.length);
		// System.out.println("totalCount : " + totalCount);

		return paging(totalCount, currentPage, 6);
	}

	// 공통 부분 따로 메서드로 뺌
	public PagingDTO paging(int totalCount, int currentPage, int countPerPage) {
		int totalPage = totalCount % countPerPage == 0 ? totalCount / countPerPage : totalCount / countPerPage + 1;
		int startPage;
		int endPage;

		if (totalPage <= 10) { // 한번에 10 페이지씩 표시되므로 총 페이지 수가 10페이지 이하면 무조건 시작페이지 = 1, 끝페이지 = 총 페이지 수
			startPage = 1;
			endPage = totalPage;
		} else { // 그 외의 경우
			// 시작페이지
			if (currentPage + 5 >= totalPage) {
				startPage = totalPage - 9;
			} else if (currentPage <= 5) {
				startPage = 1;
			} else {
				startPage = currentPage - 4;
			}

			// 끝페이지
			if (currentPage + 5 >= totalPage) {
				endPage = totalPage;
			} else if (currentPage < 6) {
				endPage = 10;
			} else {
				endPage = currentPage + 5;
			}
		}

		int startNo = (currentPage - 1) * countPerPage;
		boolean prev = currentPage > 5 ? true : false;
		boolean next = currentPage + 5 >= totalPage ? false : true;

		return new PagingDTO(currentPage, startNo, countPerPage, totalPage, startPage, endPage, prev, next);

	}

}
