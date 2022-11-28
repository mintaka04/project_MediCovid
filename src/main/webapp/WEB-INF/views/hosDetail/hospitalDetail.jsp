<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- appkey 추가위해 사용 -->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원 상세 페이지</title>
<link rel="stylesheet" href="/css/hospitalDetail.css" />
<!-- swiper css -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.css" />
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('hospitaldetail.appkey')" />">
</script>
<!-- Swiper JS -->
<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
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
				
				<div class="nav_sector"><a href="main">첫 화면으로</a></div>
				<div class="nav_sector"><a href="search/hospitals?category=1">호흡기 전담클리닉</a></div>
				<div class="nav_sector"><a href="search/hospitals?category=2">재택치료 외래센터</a></div>
				<div class="nav_sector"><a href="search/hospitals?category=3">전화상담 병의원</a></div>
				
			</div>
			<div class="chatAndLogout">
				<c:choose>
				<c:when test="${model == '권한없음'}">
					<a href="../loginForm">로그인</a>
					<a href="../register">회원가입</a>
				</c:when>
				<c:otherwise>
					<a href="../logout">로그아웃</a>
					<a href="../mypage">마이페이지</a>
				</c:otherwise>
				</c:choose>
			</div>
			
		</div>
	</div>




<!-- 내용뷰뷴 -->
	<div class="content">
		
		<div class="contentregion">
			
			<div class="searchregion">
			
				<div class="searchtool">
					
					<input type="text" name="keyword" id="searchinput" placeholder = "동네명 또는 병원명으로 검색해보세요!" onkeypress="if(event.keyCode == 13){gotoSearch();}" />
					<input type="button" id = "searchbtn" onclick="gotoSearch()" value="검색" />
					
				</div>
			
			</div>
			<div class="hopinforegion">
				
				<div class="inforeg">
					
					<div class="hop_name" id="hop_name">${hospitalInformation.hname}</div>
					<div class="hop_cat">					

						<c:if test = "${hospitalCat[0]== 'true'}">
							<div class="cat_img"><img src="../images/breathHospital.png" alt="호흡기전담"></div>
						</c:if>
						<c:if test = "${hospitalCat[2]== 'true'}">
							<div class="cat_img"><img src="../images/telHospital.png" alt="전화상담"></div>
						</c:if>
						<c:if test = "${hospitalCat[1]== 'true'}">
							<div class="cat_img"><img src="../images/homeHospital.png" alt="재택치료"></div>
						</c:if>
				
					</div>
					<div class="hop_loc">
					
						<div class="hop_loc_info">${hospitalInformation.haddress }</div>
						<div class="hop_loc_info">${hospitalInformation.htel }</div>
						<div class="hop_loc_info" id = "hitime">
							
							<div class="hitime_time" id="hitime_time">${hospitalInformation.htime }</div>
							<div class="hitime_btn">
								<input type="button" class="tim_button" onclick="makeRev()" value="예약하기" />
								<input type="button" class="tim_button" value="상담채팅" />
							</div>
						
						</div>
					
					</div>
					<div class="hop_star">
						
						<div class="hop_star_tle">평균 평점</div>
						<div class="hop_star_star">
						
							<div class="star_title">
							
								<div class="stitle_title">의사 진료 만족도</div>
								<div class="stitle_title">간호사 친절성</div>
								<div class="stitle_title">병원 내부 환경</div>
								<div class="stitle_title">대기시간 만족도</div>
							
							</div>
							<div class="star_star">
							
								<div class="sstar_real" id="star_hds">
								
									<c:set var="hds" value="${hospitalInformation.hds }"/>
									<c:forEach var="i" begin='1' end ='5' step = '1'>			
										<c:choose>
											
												<c:when test = "${hds >= i}"><img src="../images/ratingStar.png" alt="별"></c:when>
												<c:otherwise><img src="../images/ratingEmptyStar.png" alt="별"></c:otherwise>
											
										</c:choose>				
									</c:forEach>
								
								</div>
								<div class="sstar_real" id="star_hns">
								
									<c:set var="hns" value="${hospitalInformation.hns }"/>
									<c:forEach var="i" begin='1' end ='5' step = '1'>			
										<c:choose>
											
												<c:when test = "${hns >= i}"><img src="../images/ratingStar.png" alt="별"></c:when>
												<c:otherwise><img src="../images/ratingEmptyStar.png" alt="별"></c:otherwise>
											
										</c:choose>				
									</c:forEach>
								
								</div>
								<div class="sstar_real" id="star_hfs">
								
									<c:set var="hfs" value="${hospitalInformation.hfs }"/>
									<c:forEach var="i" begin='1' end ='5' step = '1'>			
										<c:choose>
											
												<c:when test = "${hfs >= i}"><img src="../images/ratingStar.png" alt="별"></c:when>
												<c:otherwise><img src="../images/ratingEmptyStar.png" alt="별"></c:otherwise>
											
										</c:choose>				
									</c:forEach>
								
								</div>
								<div class="sstar_real" id="star_hts">
								
									<c:set var="hts" value="${hospitalInformation.hts }"/>
									<c:forEach var="i" begin='1' end ='5' step = '1'>			
										<c:choose>
											
												<c:when test = "${hts >= i}"><img src="../images/ratingStar.png" alt="별"></c:when>
												<c:otherwise><img src="../images/ratingEmptyStar.png" alt="별"></c:otherwise>
											
										</c:choose>				
									</c:forEach>
								
								</div>
							
							</div>
							
						</div>
					
					</div>
					
				</div>
				<div class="mapreg" id="map">
					
					<input type="hidden" id="mapX" value="${hospitalInformation.hx}" />
					<input type="hidden" id="mapY" value="${hospitalInformation.hy}" />
					
				</div>
				
				<!-- 지도 api 사용 위해 지도관련 html보다 밑에 위치해야하므로 여기에 위치함!  -->
				<script type="text/javascript" src="/js/hospitalDetail.js"></script>
				
			</div>
			<div class="pharm">
				
			    <!-- Swiper -->
			    <div class="swiper mySwiper">
				    <div class="swiper-wrapper">
				    
				    	<c:forEach var="pharm" items="${pharmList}">
				    
					        <div class="swiper-slide">
					        	
					        	<div class="innerswipe" onclick = "moveToMap(${pharm.px}, ${pharm.py })">
					        	
						        	<div class="swipe_name">${pharm.pname }</div>
						        	<div class="swipe_info">${pharm.paddress }</div>
						        	<div class="swipe_info">${pharm.ptel }</div>
						        	<div class="swipe_info">운영시간 : 09:00 ~ ${pharm.ptime }</div>
					        	
					        	</div>
					        
					        </div>
				        
				        </c:forEach>
				        
				    </div>
			    <div class="swiper-button-next"></div>
			    <div class="swiper-button-prev"></div>
			    <div class="swiper-pagination"></div>
			    </div>
			 
			
		</div>
		
	</div>

</div>


<!-- 예약하기 눌렀을 때 로그인 여부 판단해 다음 동작 하기 위함(js의 makeRev에서 사용) -->
<input type="hidden" name="" id="access_right" value="${model }" />


<!-- 예약 모달창 -->
	<div class="back_modal"></div>
	<div class="rev_modal">
		
		<div class="modal_head">
			
			<div class="modal_head_logo">
				<img src="../images/Star1.png" alt="로고" /> MediCovid			
			</div>
			<div class="modal_head_exit">
				<button type="button" id="closeHospitalRegistrationForm"><img src="../images/CloseButton.png" onclick = "modalclose()" alt="닫기" /></button>			
			</div>
		
		</div>
		<div class="modal_title">예약 진행</div>
		<div class="modal_hop_info">· ${hospitalInformation.hname}</div>
		<div class="modal_hop_info">· ${hospitalInformation.haddress}</div>
		<div class="modal_rev_date"></div>
		<div class="text_ul_wrap">
		  
			<a href="javascript:;" id="dropdown_date" >예약 날짜</a>
			<ul class="ul_select_style" id="date_ul">
			
				<c:forEach var="date" items="${date15 }" varStatus="status">				
					<li onclick="select_date('${date}', '${hospitalInformation.htime }', '${hospitalInformation.hno }')" >${date } (${day15[status.index]})</li>				
				</c:forEach>

		  	</ul>
		  	
		</div>
		<div class="text_ul_wrap">
		  
			<a href="javascript:;" id="dropdown_time">예약 시간</a>
			<ul class="ul_select_style" id="time_ul">
			
				<c:forEach var="stime" items="${startTimes }" varStatus="tst">				
					<li id="${tst.index }">${stime}</li>				
				</c:forEach>

		  	</ul>
		  	
		</div>
		<div class="modal_text_input">
		
			<input type="text" name="" id="name_ul" placeholder="예약자명을 입력해주세요"/>
		
		</div>
		<div class="modal_text_input">
		
			<input type="text" name="" id="phone_ul" placeholder="연락처('-'없이 입력해주세요)"/>
		
		</div>
		<div class="modal_text_input">
		
			<input type="text" name="" id="modal_text_input_desc" placeholder="특이사항이 있다면 작성해주세요"/>
		
		</div>
		<div class="modal_btn">
		
			<input type="button" id="modal_btn_btn" onclick = "addResv('${hospitalInformation.hno }')" value="예약 등록" />
		
		</div>
	</div>







<!-- footer 부분 -->
	<div class="footer">		
		<div class="footerregion">
		
			<div class="footer_left">
			
				<div class="footer_nav">
				
					<div class="fnav_sector"><a href="#">이용약관</a></div>
					<div class="fnav_sector"><a href="#">개인정보 취급방침</a></div>
					<div class="fnav_sector"><a href="board">공지사항</a></div>
					<div class="fnav_sector"><a href="#">사이트맵</a></div>
					<div class="fnav_sector"><a href="#">병원가입 안내</a></div>
				
				</div>
				<div class="footer_info">
					
					<div class="info_sector">서울 종로구 율곡로10길 105 디아망 4층</div>
					<div class="info_sector">(주)MediCovid | 대표자 : 황경화 | 사업자등록번호 : 111-11-11111 | 통신판매신고 : 제2022-011호</div>
					<div class="info_sector">개인정보관리자 : 김민식(kms@mediCovid.com)</div>
					<div class="info_sector">Copyright © 2022 MediCovid All Rights Reserved</div>
					
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


<script type="text/javascript" src="/js/pharmSwipe.js"></script>
</body>
</html>