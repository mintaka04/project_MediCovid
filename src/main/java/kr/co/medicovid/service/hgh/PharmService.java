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

	
	public List<PharmDTO> pharmList(String hx, String hy) throws IOException, ParseException{
		
		List<PharmDTO> listpharm = new ArrayList<>();
		
		//공공 데이터 처리----
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
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			
			JSONObject obj1 = (JSONObject)jsonObject.get("TbPharmacyOperateInfo");
			
			JSONArray jsonArray = (JSONArray)obj1.get("row");
			
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
				
				//km 단위
				if(length <= 0.3) {
					String pname = obj.get("DUTYNAME").toString();
					String paddress = obj.get("DUTYADDR").toString();
					String ptel = obj.get("DUTYTEL1").toString();
					String pretime = obj.get("DUTYTIME1C").toString();
					String ptime = pretime.substring(0, 2)+":"+pretime.substring(2,4);
					
					System.out.println(pname+" : "+paddress+" : "+ptel+" : "+ptime);
					PharmDTO dto = new PharmDTO(pname, paddress, ptel, ptime, px, py);
					listpharm.add(dto);
				}
			}
			rd.close();
			conn.disconnect();
		}
		
		
		
		
		
		return listpharm;
	}
	
	
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
