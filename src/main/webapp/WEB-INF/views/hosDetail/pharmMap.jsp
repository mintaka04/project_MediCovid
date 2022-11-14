<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
#pharmMap{
	width : 800px;
	height : 600px;
}
</style>
<body>

	<div id="pharmMap">지도영역!</div>
	
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('pharmmap.appkey')" />"></script>
	<script>
	
		function getParameter(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		        results = regex.exec(location.search);
		    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
	
	
		var py = getParameter("py");
		var px = getParameter("px");
	
		var mapContainer = document.getElementById('pharmMap'), // 지도를 표시할 div 
		mapOption = {
			center: new kakao.maps.LatLng(py, px), // 지도의 중심좌표
			level: 3, // 지도의 확대 레벨
			mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
		};
	
		// 지도를 생성한다 
		var map = new kakao.maps.Map(mapContainer, mapOption);
		
		//지도에 마커 생성
		var marker = new kakao.maps.Marker({
			position: new kakao.maps.LatLng(py, px), // 마커의 좌표
			map: map // 마커를 표시할 지도 객체
		});	
	
	
	</script>
</body>
</html>