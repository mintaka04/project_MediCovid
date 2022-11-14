package kr.co.medicovid.control.lsh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.medicovid.dao.lsh.BoardDAO;
import kr.co.medicovid.dto.BoardDTO;
import kr.co.medicovid.dto.PagingDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;

@Controller
public class BoardController {

	@Autowired
	BoardDAO dao;

	@Autowired
	Paging pg;

	// 공지사항 가기
	@GetMapping("/board")
	public String notice(@RequestParam(value = "cp", defaultValue = "1") int cp,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		PagingDTO dto = pg.boardPaging(cp);
		List<BoardDTO> list = dao.selectAll(dto.getStartNo());

		model.addAttribute("dto", dto); // 페이징 변수값들
		model.addAttribute("list", list); // 공지사항 리스트

		// 관리자로 로그인했을 때만 게시판에 글쓰기,수정,삭제 버튼 보여줘야 하므로 principalDetails의 type 이 user일때만 모델에 세션 추가
		if (principalDetails != null) {
			if (principalDetails.getType().equals("user")) {
				model.addAttribute("session", principalDetails.getUdto());
			}
		}

		return "notice";
	}

	// 공지사항 자세히 보기
	@GetMapping("/board/{bno}")
	@ResponseBody
	public BoardDTO showNoticeDetail(@PathVariable("bno") int bno) {
		return dao.selectOne(bno);
	}

	// 공지사항 등록
	@PostMapping("/board")
	public String insertOne(@RequestParam("title") String title, @RequestParam("content") String content) {
		dao.insertOne(title, content);

		return "redirect:/board";
	}

	// 공지사항 수정
	@PutMapping("/board/{bno}")
	public String editNotice(@PathVariable("bno") int bno, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		dao.updateOne(title, content, bno);

		return "redirect:/board";
	}

	// 공지사항 삭제
	@DeleteMapping("/board/{bno}")
	public String deleteNotice(@PathVariable("bno") int bno) {
		dao.deleteOne(bno);

		return "redirect:/board";
	}

}
