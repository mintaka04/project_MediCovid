package kr.co.medicovid.dao.msk;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class XmltoJava {
	
	@Value("${xmolto.appkey}")
	static String xkey;

	// tag 값의 정보를 가져오는 함수
	public static String getTagValue(String tag, Element eElement) {
		// 결과를 저장할 result 변수 선언
		String result = "";
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		result = nlList.item(0).getTextContent();
		return result;
	}

	// tag값의 정보를 가져오는 함수
	public static String getTagValue(String tag, String childTag, Element eElement) {

		// 결과를 저장할 result 변수 선언
		String result = "";

		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

		for (int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

			// result += nlList.item(i).getFirstChild().getTextContent() + " ";
			result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
		}

		return result;
	}

	public static void main(String[] args) {

		
		LocalDate date = LocalDate.now();
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
		String Yesterday = date.minusDays(1).format(fm);
		
		try {
			
			// parsing 할 url 지정(API 키 포함해서)
			String url = "http://openapi.seoul.go.kr:8088/"+ xkey +"/xml/TbCorona19CountStatusJCG/1/5/"+Yesterday+"00";
			
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			// 제일 첫번째 태그
			doc.getDocumentElement().normalize();
			
			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("row");
			
			for(int temp = 0; temp < nList.getLength(); temp++) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				
				
				map.put("JONGNOADD", getTagValue("JONGNOADD", eElement));
				map.put("JUNGGUADD", getTagValue("JUNGGUADD", eElement));
				map.put("YONGSANADD", getTagValue("YONGSANADD", eElement));
				map.put("SEONGDONGADD", getTagValue("SEONGDONGADD", eElement));
				map.put("GWANGJINADD", getTagValue("GWANGJINADD", eElement));
				map.put("DDMADD", getTagValue("DDMADD", eElement));
				map.put("JUNGNANGADD", getTagValue("JUNGNANGADD", eElement));
				map.put("SEONGBUKADD", getTagValue("SEONGBUKADD", eElement));
				map.put("GANGBUKADD", getTagValue("GANGBUKADD", eElement));
				map.put("DOBONGADD", getTagValue("DOBONGADD", eElement));
				map.put("NOWONADD", getTagValue("NOWONADD", eElement));
				map.put("EPADD", getTagValue("EPADD", eElement));
				map.put("SDMADD", getTagValue("SDMADD", eElement));
				map.put("MAPOADD", getTagValue("MAPOADD", eElement));
				map.put("YANGCHEONADD", getTagValue("YANGCHEONADD", eElement));
				map.put("GANGSEOADD", getTagValue("GANGSEOADD", eElement));
				map.put("GUROADD", getTagValue("GUROADD", eElement));
				map.put("GEUMCHEONADD", getTagValue("GEUMCHEONADD", eElement));
				map.put("YDPADD", getTagValue("YDPADD", eElement));
				map.put("DONGJAKADD", getTagValue("DONGJAKADD", eElement));
				map.put("GWANAKADD", getTagValue("GWANAKADD", eElement));
				map.put("SEOCHOADD", getTagValue("SEOCHOADD", eElement));
				map.put("GANGNAMADD", getTagValue("GANGNAMADD", eElement));
				map.put("SONGPAADD", getTagValue("SONGPAADD", eElement));
				map.put("GANGDONGADD", getTagValue("GANGDONGADD", eElement));
				
				JSONObject jsonObject = new JSONObject(map);
				
				System.out.println(jsonObject);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}