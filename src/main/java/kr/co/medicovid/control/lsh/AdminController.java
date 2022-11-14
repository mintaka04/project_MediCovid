package kr.co.medicovid.control.lsh;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.medicovid.dao.lsh.AdminDAO;
import kr.co.medicovid.dto.HospitalInfoDTO;

@Controller
@RequestMapping("/admin") // ROLE_ADMIN 인 사용자만 들어올 수 있는 URL
public class AdminController {

	@Autowired
	AdminDAO dao;

	@Autowired
	Paging pg;

	// 관리자 메인으로
	@GetMapping("/main")
	public String list() {

		return "adminMain";
	}

	// 병원리스트 가져오기 ajax 용
	@GetMapping("/hospitals")
	@ResponseBody
	public HashMap<String, Object> selectAllHospitals(@RequestParam(value = "cp", defaultValue = "1") int currentPage) {
		HashMap<String, Object> map = new HashMap<>();

		map.put("hospitalList", dao.selectHospitalsAll(pg.hospitalPaging(currentPage).getStartNo()));
		map.put("hospitalPaging", pg.hospitalPaging(currentPage));

		return map;
	}

	// 병원 1개 가져오기 ajax 용
	@GetMapping("/hospitals/{hno}")
	@ResponseBody
	public HospitalInfoDTO selectHospitalByHno(@PathVariable("hno") int hno) {

		return dao.selectOne(hno);
	}

	// 사용자 리스트 가져오기 ajax 용
	@GetMapping("/users")
	@ResponseBody
	public HashMap<String, Object> selectUsersAll(@RequestParam(value = "cp", defaultValue = "1") int currentPage) {
		
		HashMap<String, Object> map = new HashMap<>();

		map.put("userList", dao.selectUsersAll(pg.userPaging(currentPage).getStartNo()));
		map.put("userPaging", pg.userPaging(currentPage));

		return map;
	}

	// 사용자 1명 가져오기 ajax 용
	@GetMapping("/users/{uid}")
	@ResponseBody
	public HashMap<String, Object> selectByUid(@PathVariable("uid") String uid,
			@RequestParam(value = "cp", defaultValue = "1") int currentPage) {
		
		HashMap<String, Object> map = new HashMap<>();

		map.put("userList", dao.selectByUid(uid, pg.userPaging(currentPage).getStartNo()));
		map.put("userPaging", pg.userSearchPaging(currentPage, uid));
		
		return map;
	}

	// 병원 등록
	@PostMapping("/hospitals")
	public String registerHospital(@ModelAttribute("infodto") HospitalInfoDTO infodto,
			@RequestParam("ccategory") String ccategory) {
		// 병원등록폼으로부터 병원등록 값 가져와 insert
		dao.insertNewHospital(infodto);

		// 방금 등록 된 병원의 hno 가져옴
		HospitalInfoDTO dto = dao.selectJustAddedHospital();
		// name이 ccategory 인 체크박스에 체크된 값 가져와서 체크된 값이 2개이상이면 콤마로 구분하여 배열에 담기
		String[] strArr = ccategory.split(",");

		// 배열 갯수만큼 HospitalCategory 테이블에 insert
		for (String category : strArr) {
			dao.insertHospitalCategory(Integer.parseInt(category), dto.getHno());
		}

		return "redirect:/admin/main";
	}

	// 사용자 패널티 부여
	@PutMapping("/users/{uno}")
	public String modifyPenalty(@PathVariable("uno") int uno, @RequestParam("penalty") int penalty) {
		dao.modifyPenalty(penalty, uno);

		return "redirect:/admin/main";
	}

	// 병원정보 수정
	@PutMapping("/hospitals/{hno}")
	public String updateHospital(@PathVariable("hno") int hno, @ModelAttribute("infodto") HospitalInfoDTO infodto,
			@RequestParam("ccategory") String ccategory) {
		//System.out.println("infodto : " + infodto);
		//System.out.println("cateory : " + ccategory);

		infodto.setHno(hno);

		// 병원등록폼으로부터 병원등록 값 가져와 update
		dao.updateNewHospital(infodto);
		// 수정된 병원의 기존에 등록되어있던 카테고리 모두 삭제
		dao.deleteHospitalCategory(hno);

		// name이 ccategory 인 체크박스에 체크된 값 가져와서 체크된 값이 2개이상이면 콤마로 구분하여 배열에 담기
		String[] strArr = ccategory.split(",");

		// 카테고리 재등록
		for (String category : strArr) {
			dao.insertHospitalCategory(Integer.parseInt(category), hno);
		}

		return "redirect:/admin/main";
	}

	// 병원 삭제
	@DeleteMapping("/hospitals/{hno}")
	public String deleteHospital(@PathVariable("hno") int hno) {
		dao.deleteHospital(hno);
		dao.deleteHospitalCategory(hno);

		return "redirect:/admin/main";
	}

	// 사용자 삭제
	@DeleteMapping("/users/{uno}")
	public String deleteUser(@PathVariable("uno") int uno) {
		dao.deleteUser(uno);

		return "redirect:/admin/main";
	}
}
