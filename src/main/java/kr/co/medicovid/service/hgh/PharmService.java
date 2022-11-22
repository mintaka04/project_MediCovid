package kr.co.medicovid.service.hgh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.medicovid.dto.PharmDTO;

@Service
public class PharmService {
	
	@Value("${pharmservice.appkey}")
	String oakey;

	
	/*
	 * 병원 x, y 값 가져와서
	 * 공공데이터 약국 목록의 위치정보와 비교
	 * 0.3km이내일 경우 list에 저장
	 */
	public List<PharmDTO> pharmList(String hx, String hy) throws IOException, ParseException{
		
		List<PharmDTO> listpharm = new ArrayList<>();
		
		
		//데이터 개수가 1000개를 넘어가서 for문 처리
		for(int i = 0; i <= 5; i++) {
			
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
			urlBuilder.append("/" + URLEncoder.encode(oakey, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode("TbPharmacyOperateInfo", "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(i+"000", "UTF-8")); 
			urlBuilder.append("/" + URLEncoder.encode(i+"999", "UTF-8"));
			
			
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
			}
			
			String result = rd.readLine();
			
			
			//json 객체로 만들어 원하는 정보만 추출
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);			
			JSONObject obj1 = (JSONObject)jsonObject.get("TbPharmacyOperateInfo");			
			JSONArray jsonArray = (JSONArray)obj1.get("row");
			
			
			//해당 정보중 위치정보만 추출
			for(int j = 0; j < jsonArray.size(); j++) {
				JSONObject obj = (JSONObject)jsonArray.get(j);
				
				String px = obj.get("WGS84LON").toString();
				String py = obj.get("WGS84LAT").toString();
				
				
				//거리계산해주기
				double dx1 = Double.parseDouble(hx);
				double dy1 = Double.parseDouble(hy);
				double dx2 = Double.parseDouble(px);
				double dy2 = Double.parseDouble(py);
				double length = getDistance_arc(dy1, dx1, dy2, dx2);
				
				
				//병원과 해당 약국의 거리가 0.3km 이하일 경우
				//해당 약국의 정보를 list에 저장
				if(length <= 0.3) {
					String pname = obj.get("DUTYNAME").toString();
					String paddress = obj.get("DUTYADDR").toString();
					String ptel = obj.get("DUTYTEL1").toString();
					String pretime = obj.get("DUTYTIME1C").toString();
					String ptime = pretime.substring(0, 2)+":"+pretime.substring(2,4);
					
					//System.out.println(pname+" : "+paddress+" : "+ptel+" : "+ptime);
					PharmDTO dto = new PharmDTO(pname, paddress, ptel, ptime, px, py);
					listpharm.add(dto);
				}
			}
			rd.close();
			conn.disconnect();
		}
		
		return listpharm;
	}
	
	
	//거리 계산해 km 단위로 반환
	private static double getDistance_arc(double sLat, double sLong, double dLat, double dLong){
		final int radius=6371009;
		double uLat=Math.toRadians(sLat-dLat);
		double uLong=Math.toRadians(sLong-dLong);
		double a = Math.sin(uLat/2) * Math.sin(uLat/2) + Math.cos(Math.toRadians(sLong)) * Math.cos(Math.toRadians(dLong)) * Math.sin(uLong/2) * Math.sin(uLong/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = radius * c;
		return Double.parseDouble(String.format("%.3f", distance/1000));
	}
}
