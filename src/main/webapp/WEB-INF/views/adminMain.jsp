<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/admin.css" />
<link rel="icon" href="../images/Star1.png">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('adminmain.appkey')" />&libraries=services"></script>
<script type="text/javascript" src="/js/admin.js"></script>
<title>MediCovid</title>
</head>

<body>
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="../main">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</a>
			</div>
			<div class="chatAndLogout">
				<a href="../chat/adminChat" target="_blank">채팅</a>
				<a href="../logout">로그아웃</a>
			</div>
		</div>
	</div>
	<div class="content">
		<div class="h2AndSearchDiv">
			<h2>병원 관리</h2>
			<div class="searchDiv searchDiv1">
				<input type="text" name="hno" class="searchInput"
					id="hospitalKeyword" placeholder="병원 아이디(번호)입력">
				<input type="button" value="검색" class="searchBtn">
			</div>
		</div>
		<!-- 병원등록 Modal -->
		<div class="modal" id="hospitalRegistrationForm">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5>
							<img src="../images/Star1.png" alt="로고" />
							MediCovid
						</h5>
						<button type="button" id="closeHospitalRegistrationForm">
							<img src="../images/CloseButton.png" alt="닫기" />
						</button>
					</div>
					<form action="" method="post" id="hospitalFrm">
						<input type="hidden" name="_method" value="" />
						<div class="modal-body">
							<h5 class="modal-title"></h5>
							<p>
								<span>구분</span> <span class="categoryCheckbox"> <input
										type="checkbox" name="ccategory" value="1" /> 호흡기 전담 클리닉<br>
									<input type="checkbox" name="ccategory" value="2" /> 재택치료
									외래진료센터<br> <input type="checkbox" name="ccategory"
										value="3" /> 전화상담 병의원
								</span>
							</p>
							<p>
								<span>병원명</span>
								<input type="hidden" name="hno" id="hno">
								<input type="text" name="hname" id="hname" placeholder="병원명">
							</p>
							<p>
								<span>운영시간</span>
								<input type="text" id="time1" placeholder="시작 시간 예) 0900">
								<input type="text" id="time2" placeholder="종료 시간 예) 1600">
								<input type="hidden" name="htime" id="htime" />
							</p>
							<p>
								<span>주소</span>
								<input type="text" id="postcode" onclick="execDaumPostcode()"
									placeholder="우편번호">
								<input type="button" onclick="execDaumPostcode()"
									value="우편번호 찾기" />
								<br>
								<input type="text" id="address" placeholder="주소" disabled>
								<br>
								<input type="text" id="detailAddress" placeholder="상세주소">
								<input type="hidden" id="extraAddress" placeholder="참고항목">
								<input type="hidden" name="haddress" id="haddress">
								<input type="hidden" name="hgu" id="hgu" />
								<input type="hidden" name="hx" id="hx" placeholder="x" />
								<input type="hidden" name="hy" id="hy" placeholder="y" />
							</p>
							<p>
								<span>전화번호</span>
								<input type="text" name="htel" id="htel"
									placeholder="예) 02-0000-0000">
							</p>
						</div>
						<div class="modal-footer">
							<input type='button' value='등록'>
							<input type='hidden' value='삭제'>
							<input type='hidden' value='수정'>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="tableDiv tableDiv1">
			<table class="hospitalsTable">
				<thead>
					<tr>
						<th>번호</th>
						<th>병원명</th>
						<th>주소</th>
						<th>전화번호</th>
						<th>운영시간</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div class="paginationDiv paginationDiv1">
			<ul>
			</ul>
			<div class="hospitalRegistrationButtonDiv">
				<button type="button" id="hospitalRegistration">병원 등록</button>
			</div>
		</div>

		<div class="h2AndSearchDiv">
			<h2>사용자 관리</h2>
			<div class="searchDiv searchDiv2">
				<input type="text" name="search" class="searchInput"
					id="userKeyword" placeholder="회원 아이디 입력">
				<input type="button" value="검색" class="searchBtn">
			</div>
		</div>
		<div class="tableDiv tableDiv2">
			<table class="usersTable">
				<thead>
					<tr>
						<th>번호</th>
						<th>사용자명</th>
						<th>아이디</th>
						<th>연락처</th>
						<th>패널티 일수</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div class="paginationDiv paginationDiv2">
			<ul>
			</ul>
		</div>
	</div>
	<!-- 패널티 추가/삭제 Modal -->
	<div class="modal" id="userPenaltyForm">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>
						<img src="../images/Star1.png" alt="로고" />
						MediCovid
					</h5>
					<button type="button" id="closeUserPenaltyForm">
						<img src="../images/CloseButton.png" alt="닫기" />
					</button>
				</div>
				<form action="" method="post" id="userFrm">
					<input type="hidden" name="_method" value="" />
					<div class="modal-body">
						<h5 class="modal-title">사용자 정보 관리</h5>
						<p>
							ㆍ 사용자명 : <span></span>
						</p>
						<p>
							ㆍ 아이디 : <span class="uid"></span>
						</p>
						<p>
							<span>패널티</span>
							<input type="text" name="penalty"
								placeholder="적용할 패널티 일수를 입력해주세요" />
						</p>
					</div>
					<div class="modal-footer">
						<input type='button' value='회원 삭제'>
						<input type='button' value='패널티 적용'>
					</div>
				</form>
			</div>
		</div>
	</div>

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
</body>

</html>