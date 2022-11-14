<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원관리자메인페이지</title>
<link rel="stylesheet" href="/css/hospitalMain.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript" src="/js/hospitalMain.js"></script>
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

			<div class="chatAndLogout">
				<a href="logout">로그아웃</a>
				<a href="#">채팅</a>
			</div>

		</div>
	</div>




	<!-- 내용부분 -->
	<div class="content">

		<!-- 일일 예약 관리 제목, 날짜, 버튼 부분  -->
		<div id="region1">
			<div class="name1AndButtons">

				<div class="name1">일일 예약 관리</div>

				<div class="daysButtons">
					<button class="btns" id="btnbefore" onclick="dateMinus()">이전날</button>
					<div id="ddate">${currentDate }</div>
					<button class="btns" id="btnafter" onclick="datePlus()">다음날</button>
				</div>

			</div>



			<div class="datelistresult" id="tb1">

				<!-- 오늘 기준으로 며칠 증가되었는지 나타내기 위함 -->
				<input type="hidden" id="datehidden" value="0" />


				<table class="table1">
					<tr>
						<th width="8%" id="th_b">예약번호</th>
						<th width="8%">시간</th>
						<th width="12%">이름</th>
						<th width="12%">연락처</th>
						<th width="50%">특이사항</th>
						<th width="10%">내방확인</th>
					</tr>

					<c:forEach var="dto" items="${dateResvRecord }">
						<tr>
							<td><div class="td_1">${dto.rno}</div></td>
							<td><div class="td_2">${dto.rtime}</div></td>
							<td><div class="td_3">${dto.rname}</div></td>
							<td><div class="td_4">${dto.rtel}</div></td>
							<td><div class="td_5">${dto.rremark}</div></td>

							<c:choose>
								<c:when test="${dto.rstatus == 3}">
									<td><div class="td_6">
											<input type="button" class="visitconf" id="btn${dto.rno }"
												onclick="buttonToggle(${dto.rno});" value="내방취소" />
										</div></td>
								</c:when>
								<c:otherwise>
									<td><div class="td_6">
											<input type="button" class="visitconf" id="btn${dto.rno }"
												onclick="buttonToggle(${dto.rno});" value="내방확인" />
										</div></td>
								</c:otherwise>
							</c:choose>

						</tr>
					</c:forEach>

				</table>


			</div>
		</div>

		<div class="name1AndButton">
			<div class="name1">과거 예약 내역</div>
			<div class="radiobtn">
				<input type="radio" class="catdate" name="catdate" id="radio7"
					onclick="checkfilter(this)" value="7일전" checked />
				7일전
				<input type="radio" class="catdate" name="catdate" id="radio30"
					onclick="checkfilter(this)" value="30일전" />
				30일전
				<input type="radio" class="catdate" name="catdate" id="radio90"
					onclick="checkfilter(this)" value="90일전" />
				90일전

			</div>
		</div>


		<!-- reservation 테이블에서 rstatus에서 3인것만 출력... -->

		<div class="pastlistresult" id="pastlistresult">

			<table class="table2">
				<tr>
					<th width="8%" id="th_b">예약번호</th>
					<th width="10%">날짜</th>
					<th width="8%">시간</th>
					<th width="12%">이름</th>
					<th width="12%">연락처</th>
					<th>특이사항</th>
				</tr>

				<c:forEach var="dto" items="${pastResvRecord }">
					<tr>
						<td><div class="td2_1">${dto.rno}</div></td>
						<td><div class="td2_2">${dto.rdate}</div></td>
						<td><div class="td2_3">${dto.rtime}</div></td>
						<td><div class="td2_4">${dto.rname}</div></td>
						<td><div class="td2_5">${dto.rtel}</div></td>
						<td><div class="td2_6">${dto.rremark}</div></td>
					</tr>
				</c:forEach>

			</table>

		</div>

		<div class="bottom_btm">

			<div class="bottom_text">* '예약 인원 설정' 버튼을 클릭하시면 시간대별 접수인원을 수정할
				수 있습니다.</div>
			<div class="bottom_btn">
				<input type="button" onclick="setInfo()" value="병원 정보 수정" />
				<input type="button" onclick="gotoRevperTime()" value="예약 인원 설정" />
			</div>

		</div>

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
						<a href="#">공지사항</a>
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