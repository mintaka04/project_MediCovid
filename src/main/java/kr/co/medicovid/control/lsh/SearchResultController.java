package kr.co.medicovid.control.lsh;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.lsh.SearchResultDAO;
import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;

@Controller
@RequestMapping("/search")
public class SearchResultController {

	@Autowired
	SearchResultDAO dao;

	@Autowired
	Paging pg;

	// 조건에 맞는 병원 리스트 가져오기
	// category는 선택하지 않으면 기본값 0으로 들어오고, cp는 선택하지 않으면 기본값 1로 들어옴
	// keyword와 gu는 없으면 없는대로
	@GetMapping("/hospitals")
	public ModelAndView search(@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestParam(value = "category", defaultValue = "0") int category,
			@RequestParam(value = "cp", defaultValue = "1") int cp,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "gu", required = false) String gu) {

		// 필터에서 gu를 선택해서 값이 들어오면 ,로 분리해서 guArr 배열에 담음 => dao에서 dynamic query 만들때 foreach
		// 쓰려면 배열이 되야해서
		String[] guArr = {};
		if (gu != null) {
			guArr = gu.split(",");
		}

		int startNo = pg.searchPaging(cp, keyword, guArr, category).getStartNo();

		// 현재 페이지에 표시될 조건에 맞는 병원 리스트 구해옴
		List<HospitalInfoDTO> hospitalInfoList = dao.selectByFilter(category, keyword, guArr, startNo);

		ArrayList<Integer> hnoList = new ArrayList<>();
		// 구해온 병원 리스트에서 hno만 뽑아서 hnoList 배열에 담음(병원배열에 담긴 hno에 해당하는 카테고리 리스트 구해오기위해서)
		// 검색결과 페이지에서 병원 표시할 때 호흡기, 재택, 전화상담 중 어떤 카테고리들에 해당하는지 표시해야 하므로 하는 작업임
		for (HospitalInfoDTO dto : hospitalInfoList) {
			hnoList.add(dto.getHno());
		}

		ModelAndView mav = new ModelAndView();

		mav.addObject("list", dao.selectByFilter(category, keyword, guArr, startNo)); // 병원리스트
		mav.addObject("dto", pg.searchPaging(cp, keyword, guArr, category)); // 페이징
		mav.addObject("categoryForHno", dao.selectCategoryByFilter(category, keyword, guArr, hnoList)); // 병원리스트 있는 병원들의
																										// 카테고리 리스트

		// 페이지 이동해도 검색조건 그대로 적용되서 페이지 이동하기 위해서
		// 키워드 값이 들어왔으면 그대로 키워드 돌려보냄
		if (keyword != null) {
			mav.addObject("keyword", keyword);
		}
		// 구 값이 들어왔으면 그대로 구 값 돌려보냄
		if (gu != null) {
			mav.addObject("gu", gu);
		}
		// 카테고리 값이 들어왔으면 그대로 카테고리 값 돌려보냄
		if (category != 0) {
			mav.addObject("category", category);
		}

		// 세션이 있으면 세션 저장
		if (principalDetails != null) {
			if (principalDetails.getType().equals("user")) {
				mav.addObject("session", principalDetails.getUdto());
			}
		}

		mav.setViewName("searchResult");

		return mav;
	}

}
