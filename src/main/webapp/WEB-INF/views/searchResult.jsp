<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/searchResult.css" />
<link rel="icon" href="../images/Star1.png">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('searchresult.appkey')" />"></script>
<script type="text/javascript" src="/js/searchResult.js"></script>
<title>MediCovid</title>
</head>

<body>
	<!-- 헤더 -->
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="../main">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</a>
			</div>
			<div class="nav">
				<ul>
					<li><a href="../main">첫 화면으로</a></li>
					<li><a href="./hospitals?category=1">호흡기 전담클리닉</a></li>
					<li><a href="./hospitals?category=2">재택진료 외래센터</a></li>
					<li><a href="./hospitals?category=3">전화상담 병의원</a></li>
				</ul>
			</div>
			<div class="chatAndLogout">
				<c:if test="${session.role == 'ROLE_USER' }">
					<a href="../mypage">마이페이지</a>
					<a href="../logout">로그아웃</a>
				</c:if>
				<c:if test="${session == null }">
					<a href="../register">회원가입</a>
					<a href="../loginForm">로그인</a>
				</c:if>
			</div>
		</div>
	</div>

	<!-- 본문 -->
	<div class="content">

		<div class="searchDiv">
			<form action="./hospitals">
				<input type="text" name="keyword" id="keyword"
					placeholder="동네명 또는 병원명으로 검색해보세요!"
					value="<c:if test='${keyword != null}'>${keyword }</c:if>">
				<input type="submit" value="검색">
			</form>
		</div>

		<div class="sideAndListDiv">
			<div class="sidebar">
				<div class="mapDiv">
					<span>병원 위치</span>
					<div id="map"></div>
				</div>
				<form action="./hospitals">
					<div class="filterDiv">
						<span>조건 적용</span>
						<input type="submit" value="필터적용">
						<input type="hidden" name="keyword"
							value="<c:if test='${keyword != null}'>${keyword }</c:if>" />
						<div class="checkboxDiv">
							<input type="checkbox" name="gu" value="도봉구,강북구,성북구,노원구"
								<c:if test="${fn:contains(gu, '도봉구,강북구,성북구,노원구')}" >checked</c:if>>
							<span>도봉/강북/성북/노원</span>
							<br>
							<input type="checkbox" name="gu" value="동대문구,중랑구,성동구,광진구"
								<c:if test="${fn:contains(gu, '동대문구,중랑구,성동구,광진구')}" >checked</c:if>>
							<span>동대문/중랑/성동/광진</span>
							<br>
							<input type="checkbox" name="gu" value="강동구,송파구"
								<c:if test="${fn:contains(gu, '강동구,송파구')}" >checked</c:if>>
							<span>강동/송파</span>
							<br>
							<input type="checkbox" name="gu" value="서초구,강남구"
								<c:if test="${fn:contains(gu, '서초구,강남구')}" >checked</c:if>>
							<span>서초/강남</span>
							<br>
							<input type="checkbox" name="gu" value="동작구,관악구,금천구"
								<c:if test="${fn:contains(gu, '동작구,관악구,금천구')}" >checked</c:if>>
							<span>동작/관악/금천</span>
							<br>
							<input type="checkbox" name="gu" value="강서구,양천구,영등포구,구로구"
								<c:if test="${fn:contains(gu, '강서구,양천구,영등포구,구로구')}" >checked</c:if>>
							<span>강서/양천/영등포/구로</span>
							<br>
							<input type="checkbox" name="gu" value="은평구,마포구,서대문구"
								<c:if test="${fn:contains(gu, '은평구,마포구,서대문구')}" >checked</c:if>>
							<span>은평/마포/서대문</span>
							<br>
							<input type="checkbox" name="gu" value="종로구,중구,용산구"
								<c:if test="${fn:contains(gu, '종로구,중구,용산구')}" >checked</c:if>>
							<span>종로/중구/용산</span>
							<hr>
							<input type="checkbox" name="category" value="1"
								<c:if test="${fn:contains(category, '1')}" >checked</c:if>>
							<span>호흡기 전담클리닉</span>
							<br />
							<input type="checkbox" name="category" value="2"
								<c:if test="${fn:contains(category, '2')}" >checked</c:if>>
							<span>재택진료 외래센터</span>
							<br />
							<input type="checkbox" name="category" value="3"
								<c:if test="${fn:contains(category, '3')}" >checked</c:if>>
							<span>전화상담 병의원</span>
						</div>
					</div>
				</form>
			</div>
			<div class="listDiv">
				<div class="desc">
					<ul>
						<li><img src="../images/cough.png" alt="호흡기 전담클리닉"><span>호흡기
								전담클리닉</span></li>
						<li><img src="../images/telephone.png" alt="전화상담 병의원"><span>전화상담
								병의원</span></li>
						<li><img src="../images/welfare.png" alt="재택진료 외래센터"><span>재택진료
								외래센터</span></li>
					</ul>
				</div>

				<c:forEach var="dto" items="${list }">
					<a href="../Hospital?hno=${dto.hno }">
						<div class="list">
							<div class="top">
								<span class="hospitalName">${dto.hname }</span>
								<ul>
									<c:forEach var="cateForHno" items="${categoryForHno }">
										<c:if
											test="${cateForHno.hno == dto.hno && cateForHno.ccategory == 1 }">
											<li><img src="../images/cough.png" alt="호흡기 전담클리닉"></li>
										</c:if>
										<c:if
											test="${cateForHno.hno == dto.hno && cateForHno.ccategory == 2 }">
											<li><img src="../images/welfare.png" alt="재택진료 외래센터"></li>
										</c:if>
										<c:if
											test="${cateForHno.hno == dto.hno && cateForHno.ccategory == 3 }">
											<li><img src="../images/telephone.png" alt="전화상담 병의원"></li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
							<div class="bottom">
								<p>${fn:split(dto.haddress,'(')[0] }</p>
								<p>${dto.htel }</p>
								<c:set var="tel" value="${dto.htime }" />
								<p>운영시간 : ${fn:substring(tel,0,2)}:${fn:substring(tel,2,4)}
									~ ${fn:substring(tel,4,6)}:${fn:substring(tel,6,8)}</p>
								<input type="hidden" name="hx" value="${dto.hx }" />
								<input type="hidden" name="hy" value="${dto.hy }" />
							</div>
						</div>
					</a>
				</c:forEach>

				<!-- 페이징 -->
				<div class="paginationDiv">
					<ul class="pagination">
						<c:if test="${dto.prev }">
							<li class='arrow prev'><img src='../images/left.png'
									alt='10페이지 이전으로' /></li>
						</c:if>

						<c:forEach var="i" begin="${dto.startPage }" end="${dto.endPage }">
							<input type="hidden" class="ttlPage" value="${dto.totalPage }" />
							<c:if test="${dto.currentPage == i}">
								<li class='pageButton currentPage'>${i }</li>
							</c:if>
							<c:if test="${dto.currentPage != i}">
								<li class='pageButton'>${i }</li>
							</c:if>
						</c:forEach>
						<c:if test="${dto.next }">
							<li class='arrow next'><img src='../images/right.png'
									alt='10페이지 뒤로' /></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>

	</div>
	<!-- 푸터 -->
	<div class="footer">
		<div class="container">
			<div class="logo">
				<img src="../images/Star1.png" alt="로고">
				<span>MediCovid</span>
			</div>
			<div class="link">
				<a href="#">이용약관</a>
				<a href="#">개인정보 취급방침</a>
				<a href="../board">공지사항</a>
				<a href="#">사이트맵</a>
				<a href="#">병원가입 안내</a>
			</div>
			<div class="etcInfo">
				<p>서울 종로구 율곡로10길 105 디아망 4층</p>
				<p>
					(주)MediCovid | 대표자 : 황경화 | 사업자등록번호 : 111-11-11111 | 통신판매신고 :
					제2022-011호
					<br>
					개인정보관리자 : 김민식(kms@medicovid.com)
				</p>
				<p>COPYRIGHT 2022 MEDICOVID ALL RIGHTS RESERVED</p>
			</div>
		</div>
	</div>

</body>

</html>
