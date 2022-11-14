package kr.co.medicovid.control.kjh;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.kjh.UserDAO;

@Controller
public class UsersController {
	@Autowired
	UserDAO dao;

	// 내 정보 수정(모달창) => 회원 탈퇴
	@RequestMapping("/userdrop")
	public String deleteuser(@RequestParam("uno") int uno) {
		System.out.println("ajax : " + uno);
		dao.delete(uno);

		return "redirect:main";
	}

}
