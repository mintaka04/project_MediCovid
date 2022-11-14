package kr.co.medicovid.control.msk;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.co.medicovid.dao.msk.MainDAO;
import kr.co.medicovid.dao.msk.XmltoJava;
import kr.co.medicovid.dto.HospitalInfoDTO;
import kr.co.medicovid.security.hjj.auth.PrincipalDetails;

@Controller
public class MainController {

	@Autowired
	MainDAO dao;

	@Value("${maincontroller.appkey}")
	String key;

	@GetMapping(value = { "/main", "/" })
	public String main(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		if (principalDetails != null) {
			if (principalDetails.getType().equals("user")) {
				if (principalDetails.getUdto().getRole().equals("ROLE_ADMIN")) {
					model.addAttribute("session", principalDetails.getUdto());
					return "redirect:admin/main";
				} else {
					model.addAttribute("session", principalDetails.getUdto());
					return "main";
				}
			} else if (principalDetails.getType().equals("hosp")) {
				model.addAttribute("session", principalDetails.getHdto());
				return "redirect:HospitalMain";
			}
		}

		return "main";
	}

	@RequestMapping("main.do")
	public String geolocation(Model model, @RequestParam("lat") String lat, @RequestParam("lon") String lon) {

		/*
		 * System.out.println("lat : " + lat); System.out.println("lon : " + lon);
		 */

		List<HospitalInfoDTO> list = dao.selectClosestHospitalsAll(lat, lon);
		// 모델에 위도/경도값 저장
		model.addAttribute("list", list);

		for (HospitalInfoDTO dto : list) {
			/* System.out.println("hcode : " + dto.getHcode() ); */
		}
		return "main";

	}

	@RequestMapping("/main")
	public String koreaList(Model model) {


		LocalDate date = LocalDate.now();
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyyMMdd");
		String endDate = date.format(fm);
		String startDate = date.minusDays(7).format(fm);

		try {

			// parsing 할 url 지정(API 키 포함해서)
			String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey="
					+ key + "&pageNo=1&numOfRows=10&startCreateDt=" + startDate + "&endCreateDt=" + endDate;

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);

			// 제일 첫번째 태그
			doc.getDocumentElement().normalize();

			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Map<String, Object> map = new HashMap<String, Object>();

				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;

				/*
				 * System.out.println("총 사망자 수 : " + getTagValue("deathCnt", eElement));
				 * System.out.println("총 감염자 수 : " + getTagValue("decideCnt", eElement));
				 * System.out.println("날짜 : " + getTagValue("stateDt", eElement));
				 */

				map.put("decideCnt", XmltoJava.getTagValue("decideCnt", eElement));
				map.put("stateDt", XmltoJava.getTagValue("stateDt", eElement));

				/* System.out.println(map.toString()); */

				JSONObject jsonObject = new JSONObject(map);

				System.out.println(jsonObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "main";
	}


}
