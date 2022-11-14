<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원 정보 수정</title>
<link rel="stylesheet" href="/css/setHospitalInfo.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/setHospitalInfo.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="icon" href="../images/Star1.png">
</head>
<body>



	<!-- header 부분 -->
	<div class="header">
		<div class="headerregion">

			<div class="logo">
				<a href="HospitalMain">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</a>
			</div>

			<div class="chatAndLogout">
				<a href="logout" id="logoutbtn">로그아웃</a>
				<a href="#" id="chatbtn">채팅</a>
			</div>

		</div>
	</div>









	<!-- 내용부분 -->
	<div class="content">

		<div class="ctitle">병원 정보 수정</div>
		<form action="hopTableEdit" method="post" id="hospitalFrm">

			<div class="content_set">

				<div class="left">

					<div class="name">

						<div class="content_set_title">병원명</div>
						<div class="content_set_input">

							<input type="text" name="hname" id="hname" value="${model.hname}"
								placeholder="변경하고자 하는 병원명을 입력해주세요">

						</div>

					</div>
					<div class="previous_pw">

						<div class="content_set_title">기존 PW</div>
						<div class="content_set_input">

							<input type="password" id="passwordinput"
								placeholder="기존 비밀번호를 입력해주세요" />
							<input type="hidden" name="hno" id="hno" value="${model.hno }" />
						</div>

					</div>
					<div class="new_pw">

						<div class="content_set_title">새 PW</div>
						<div class="content_set_input">

							<input type="password" name="hpw" id="newpw"
								placeholder="새 비밀번호를 입력해주세요" />

						</div>
					</div>
					<div class="new_pw_check">

						<div class="content_set_title">PW 확인</div>
						<div id="pwcheck"></div>
						<div class="content_set_input">

							<input type="password" id="newpwcheck" oninput="check_newpw()"
								placeholder="새 비밀번호를 한 번 더 입력해주세요" />

						</div>

					</div>
					<div class="category">

						<div class="content_set_title">구분</div>
						<div class="content_set_category">

							<input type="checkbox" class="ccc" name="category" id="category1"
								value="1" />
							호흡기 전담 클리닉
							<br>
							<input type="checkbox" class="ccc" name="category" id="category2"
								value="2" />
							재택치료 외래진료센터
							<br>
							<input type="checkbox" class="ccc" name="category" id="category3"
								value="3" />
							전화상담 병의원

							<input type="hidden" id="cat1" value="${hospitalCat[0]}" />
							<input type="hidden" id="cat2" value="${hospitalCat[1]}" />
							<input type="hidden" id="cat3" value="${hospitalCat[2]}" />

						</div>

					</div>

				</div>
				<div class="right">

					<div class="time">

						<div class="content_set_title">운영시간</div>
						<div class="content_set_time">


							<input type="text" id="time1" placeholder="시작 시간 예) 0900">
							<input type="text" id="time2" placeholder="종료 시간 예) 1600">
							<input type="hidden" id="runtime" value="${model.htime }" />
							<input type="hidden" name="htime" id="htime"
								value="${model.htime}" />

						</div>

					</div>
					<div class="address">

						<div class="content_set_title">주소</div>
						<div class="content_set_address">

							<input type="text" id="postcode" onclick="execDaumPostcode()"
								placeholder="우편번호">
							<input type="button" id="settingbtn" onclick="execDaumPostcode()"
								value="우편번호 찾기" />
							<br>
							<input type="text" id="address" placeholder="" disabled>
							<br>
							<input type="text" id="detailAddress" placeholder="상세주소">
							<input type="hidden" id="extraAddress" placeholder="참고항목">
							<input type="hidden" name="haddress" id="haddress"
								value="${model.haddress }">
							<input type="hidden" name="hgu" id="hgu" value="${model.hgu }" />
							<input type="hidden" name="hx" id="hx" placeholder="x"
								value="${model.hx }" />
							<input type="hidden" name="hy" id="hy" placeholder="y"
								value="${model.hy }" />

							<input type="hidden" id="basicaddress" value="${model.haddress }" />

						</div>

					</div>
					<div class="tel">

						<div class="content_set_title">전화번호</div>
						<div class="content_set_input">

							<input type="text" name="htel" id="htel" value="${model.htel }" />

						</div>


					</div>
					<div class="revPerTime">

						<div class="content_set_title2">시간 별 예약인원</div>
						<div class="content_set_member">

							<input type="text" name="hrevptime" id="num_mem"
								value="${model.hrevptime }">

						</div>


					</div>

				</div>

			</div>
			<div class="buttons">

				<input type='button' id="editInfo" value='병원정보 수정 완료'>

			</div>
		</form>


	</div>










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


</body>
</html>