package kr.co.medicovid.control.kjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.medicovid.dao.kjh.hospitalDAO;

@Controller
public class HospitalController {
	@Autowired
	hospitalDAO dao;

	@RequestMapping("/hospital")
	public ModelAndView hospital() {
		return new ModelAndView("list", "list", dao.selectAll());
	}
}
