<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/main.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('main.appkey')" />"></script>
<script type="text/javascript" src="/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script src="http://d3js.org/topojson.v1.min.js"></script>
<title>MediCovid</title>
<link rel="icon" href="../images/Star1.png">
</head>
<body>
	<!-- header 부분 -->
	<div class="header">
		<div class="headerregion">

			<div class="logo">
				<a href="main">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</a>
			</div>
			<div class="nav_header">

				<div class="nav_sector">
					<a href="main">첫 화면으로</a>
				</div>
				<div class="nav_sector">
					<a href="search/hospitals?category=1">호흡기 전담클리닉</a>
				</div>
				<div class="nav_sector">
					<a href="search/hospitals?category=2">재택치료 외래센터</a>
				</div>
				<div class="nav_sector">
					<a href="search/hospitals?category=3">전화상담 병의원</a>
				</div>

			</div>
			<div class="chatAndLogout">
				<c:if test="${session.role == 'ROLE_USER' }">
					<a href="logout">로그아웃</a>
					<a href="mypage">마이페이지</a>
				</c:if>
				<c:if test="${session == null }">
					<a href="loginForm">로그인</a>
					<a href="register">회원가입</a>
				</c:if>
			</div>

		</div>
	</div>

	<!-- main frame -->
	<div class="frame">
		<form class="searching" action="search/hospitals">
			<input type="text" name="keyword" id="searchBar"
				placeholder="동네명 또는 병원명으로 검색해보세요!" />
			<button class="searchBtn">검색</button>
		</form>
		<!-- searching end -->
		<div class="chart">
			<div class="seoulChart">
				<div id="tooltip"></div>
				<script>
					var width = 570, height = 325;
				
					var svg = d3.select(".seoulChart").append("svg")
							.attr("width", width).attr("height", height);
					
					var mapChart = svg.append("g").attr("id", "mapChart"),
				    	places = svg.append("g").attr("id", "places");
					
					var projection = d3.geo.mercator()
				    .center([126.9895, 37.5651])
				    .scale(50000)
				    .translate([width/2, height/2]);
				 
					var path = d3.geo.path().projection(projection);
					
					d3.json("/map/seoul_municipalities_topo_simple.json", function(error, data) {
				  	var features = topojson.feature(data, data.objects.seoul_municipalities_geo).features;
				  	
				  	var Tooltip = d3.select(".seoulChart")
				  		.append("div")
				  		.attr("class", "tooltip")
				  		.style("opacity", 1)
				  		.style("background-color", "#D95F5F")
				  		.style("color", "#f2f2f2")
      					.style("position", "absolute")
      					.style("padding", "1px")
				  		.style("text-align", "center");

				    var mouseover = function(d) {
				        Tooltip
				          .style("opacity", 1)
				      }
				    
				    var mousemove = function(d) {
				    	
				    	var now = new Date();
				    	var yesterday = new Date(now.setDate(now.getDate()-1));
				    	var month = yesterday.getUTCMonth()+1;
				    	var day = yesterday.getUTCDate();
				    	var year = yesterday.getUTCFullYear();
				    	
				    	now = year + "-" + month + "-" + day;
				    	
				        Tooltip
				          .html(d.properties.name + "<br>" + now + "<br>" + "확진자 수 : "+d.properties.covidnum+"명")
				          .style("left", (d3.mouse(this)[0]+10 ) + "px")
				          .style("top", (d3.mouse(this)[1]-50) + "px")
				          .style("z-index", 1001);
				      }
				      var mouseleave = function(d) {
				        Tooltip
				          .style("opacity", 0);
				      }
				  	
				 	mapChart.selectAll("path")
				    	.data(features)
				    	.enter().append("path")
				      	.attr("class", function(d) { console.log(); return "municipality c" + d.properties.code })
				      	.attr("d", path)
				      	.on("mouseover", mouseover)
      					.on("mousemove", mousemove)
      					.on("mouseleave", mouseleave)
				 
/* 				 	mapChart.selectAll("text")
				      	.data(features)
				    	.enter().append("text")
				      	.attr("transform", function(d) { return "translate(" + path.centroid(d) + ")"; })
				      	.attr("dy", ".35em")
				      	.attr("class", "municipality-label")
				      	.text(function(d) { return d.properties.name; }); */
				    
				    
				});
					
				</script>
			</div>
			<!-- seoulChart end -->
			<div class="koreaChart">
				<canvas id="myChart" width="570" height="325"></canvas>
				<script>
					const ctx = document.getElementById('myChart');
					Chart.defaults.font.size = 15;
					Chart.defaults.font.family = "'Noto Sans KR'";
					Chart.defaults.font.style = 'normal';
					Chart.defaults.borderColor = 'rgba(2, 115, 94, 0.1)';
					Chart.defaults.color = 'rgba(2, 115, 94, 0.4)';
					
					const myChart = new Chart(
							ctx,
							{
								type : 'line',
								data : {
									labels : [ '07-27', '07-28', '07-29',
											'07-30', '07-31' ],
									datasets : [ {
										label : '대한민국 일일 확진자 수',
										data : [ 88384, 85320, 82001, 73589,
												44689 ],
										backgroundColor : [ 'rgba(163, 191, 175, 0.2)' ],
										borderColor : [ 'rgba(2, 115, 94)' ],
										borderWidth : 2
									} ]
								},
								options : {
									scales : {
										y : {
											beginAtZero : true
										}
									}
								}
							});
				</script>
			</div>
			<!-- koreaChart end -->
			<p class="chartTitle">오늘까지의 코로나 감염자수를 알아보아요.</p>
			<!-- chartTitle end -->
			<div class="seoulChartLabel">
				<img src="/images/seoulLogo.png" alt="" id="seoulLogo" />
				<p id="seoulLogoLabel">서울특별시 구별 전일 확진자 수</p>
			</div>
			<!-- seoulChartLabel end -->
			<div class="koreaChartLabel">
				<img src="/images/taegeuk.png" alt="" id="koreaLogo" />
				<p id="koreaLogoLabel">대한민국 일일 확진자 수</p>
			</div>
			<!-- koreaChartLabel end -->
		</div>
		<!-- chart end -->

		<div class="mapArea">
			<div id="map"></div>
			<script>
				var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
				var options = { //지도를 생성할 때 필요한 기본 옵션
					center : new kakao.maps.LatLng(37.572905, 126.992599), //지도의 중심좌표.
					level : 6
				//지도의 레벨(확대, 축소 정도)
				};

				var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

				// 마우스 드래그와 모바일 터치를 이용한 지도 이동을 막는다
				map.setDraggable(false);

				// 마우스 휠과 모바일 터치를 이용한 지도 확대, 축소를 막는다
				map.setZoomable(false);

				// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
				if (navigator.geolocation) {

					// GeoLocation을 이용해서 접속 위치를 얻어옵니다
					navigator.geolocation
							.getCurrentPosition(function(position) {

								var lat = position.coords.latitude, // 위도
								lon = position.coords.longitude; // 경도

								var locPosition = new kakao.maps.LatLng(lat,
										lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
								message = '<div style="padding:10px;width:150px;color:#02735E;">현 위치는 실제 위치와 다를 수 있습니다.</div>'; // 인포윈도우에 표시될 내용입니다

								// 마커와 인포윈도우를 표시합니다
								displayMarker(locPosition, message);

								document.getElementById('lat').value = lat;
								document.getElementById('lon').value = lon;

							});

				} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

					var locPosition = new kakao.maps.LatLng(37.572905,
							126.992599), message = '현재 위치 추적이 불가능합니다.'

					displayMarker(locPosition, message);
				}

				// 지도에 마커와 인포윈도우를 표시하는 함수입니다
				function displayMarker(locPosition, message) {

					// 마커를 생성합니다
					var marker = new kakao.maps.Marker({
						map : map,
						position : locPosition
					});

					var iwContent = message, // 인포윈도우에 표시할 내용
					iwRemoveable = true;

					// 인포윈도우를 생성합니다
					var infowindow = new kakao.maps.InfoWindow({
						content : iwContent,
						removable : iwRemoveable
					});

					// 인포윈도우를 마커위에 표시합니다 
					infowindow.open(map, marker);

					// 지도 중심좌표를 접속위치로 변경합니다
					map.setCenter(locPosition);

					// 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다 
					var positions = [
							{
								content : '<div style="padding:10px;width:150px;color:#D95F5F;">'
										+ $(".closestHname1").text() + '</div>',
								latlng : new kakao.maps.LatLng($(".closestHy1")
										.val(), $(".closestHx1").val())
							},
							{
								content : '<div style="padding:10px;width:150px;color:#D95F5F;">'
										+ $(".closestHname2").text() + '</div>',
								latlng : new kakao.maps.LatLng($(".closestHy2")
										.val(), $(".closestHx2").val())
							} ];

					for (var i = 0; i < positions.length; i++) {
						// 마커를 생성합니다
						var marker = new kakao.maps.Marker({
							map : map, // 마커를 표시할 지도
							position : positions[i].latlng
						// 마커의 위치
						});

						// 마커에 표시할 인포윈도우를 생성합니다 
						var infowindow = new kakao.maps.InfoWindow({
							content : positions[i].content
						// 인포윈도우에 표시할 내용
						});

						// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
						// 이벤트 리스너로는 클로저를 만들어 등록합니다 
						// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
						kakao.maps.event.addListener(marker, 'mouseover',
								makeOverListener(map, marker, infowindow));
						kakao.maps.event.addListener(marker, 'mouseout',
								makeOutListener(infowindow));
					}

					// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
					function makeOverListener(map, marker, infowindow) {
						return function() {
							infowindow.open(map, marker);
						};
					}

					// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
					function makeOutListener(infowindow) {
						return function() {
							infowindow.close();
						};
					}

				}
			</script>
			<form method="post"
				action="${pageContext.request.contextPath }/main.do" id="gpsForm">
				<input type="hidden" id="lat" name="lat" value="" />
				<input type="hidden" id="lon" name="lon" value="" />
				<input type="submit" value="눌러서 확인하기" class="geolocationSend" />
			</form>

			<p class="mapTitle">내 주변 코로나 치료 병원은 어디에 있을까요?</p>
			<p class="closestHospitalLabel">가장 가까운 병원</p>
			<p class="closestHospital">
				<c:forEach var="dto" items="${list }" begin="0" end="0">
					<a href="Hospital?hno=${dto.hno}" id="hospitalLink">
						<span class="closestHname1">${dto.hname }</span> <span
							class="hospitalDistance"><fmt:parseNumber var="test"
								value="${dto.hcode*1000}" integerOnly="true" />, ${test }m</span>
					</a>
					<input type="hidden" value="${dto.hy }" class="closestHy1" />
					<input type="hidden" value="${dto.hx }" class="closestHx1" />
				</c:forEach>
			</p>
			<p class="nextHospitalLabel">다음으로 가까운 병원</p>
			<p class="nextHospital">
				<c:forEach var="dto" items="${list }" begin="1" end="1">
					<a href="Hospital?hno=${dto.hno}" id="hospitalLink">
						<span class="closestHname2">${dto.hname }</span> <span
							class="hospitalDistance"><fmt:parseNumber var="test"
								value="${dto.hcode*1000}" integerOnly="true" />, ${test }m</span>
					</a>
					<input type="hidden" value="${dto.hy }" class="closestHy2" />
					<input type="hidden" value="${dto.hx }" class="closestHx2" />
				</c:forEach>

			</p>
		</div>
		<!-- mapArea end -->

		<div class="mainSwiper">
			<%@include file="mainSwiper.jsp"%>
		</div>
		<!-- mainSwiper end -->
		<div class="userchat"><a href="" onclick="window.open('./chat/userChat','MediCovid','width=500 height=700')"><img src="../images/chat.png" alt="" style="top:10px; width:30px; height:30px;"/></a></div>
	</div>
	<!-- main frame end -->

	<!-- footer 부분 -->
	<div class="footer">
		<div class="footerregion">

			<div class="footer_left">

				<div class="footer_nav">

					<div class="fnav_sector">
						<a href="#">이용약관</a>
					</div>
					<div class="fnav_sector">
						<a href="#">개인정보 취급방침</a>
					</div>
					<div class="fnav_sector">
						<a href="board">공지사항</a>
					</div>
					<div class="fnav_sector">
						<a href="#">사이트맵</a>
					</div>
					<div class="fnav_sector">
						<a href="#">병원가입 안내</a>
					</div>

				</div>
				<div class="footer_info">

					<div class="info_sector">서울 종로구 율곡로10길 105 디아망 4층</div>
					<div class="info_sector">(주)MediCovid | 대표자 : 황경화 | 사업자등록번호 :
						111-11-11111 | 통신판매신고 : 제2022-011호</div>
					<div class="info_sector">개인정보관리자 : 김민식(kms@mediCovid.com)</div>
					<div class="info_sector">Copyright © 2022 MediCovid All
						Rights Reserved</div>

				</div>

			</div>
			<div class="footer_right">

				<div class="flogo">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</div>

			</div>

		</div>
	</div>
	<!-- footer end -->
</body>
</html>