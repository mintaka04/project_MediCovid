<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시간대별 인원관리</title>
<link rel="stylesheet" href="/css/hopAdminRevTime.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript" src="/js/hopAdminRevTime.js"></script>
<link rel="icon" href="../images/Star1.png">
</head>
<body>

	<!-- header 부분 -->
	<div class="header">
		<div class="headerregion">

			<div class="logo">
				<a href="/HospitalMain">
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

		<div class="nameregion">

			<div class="name1">예약 가능 인원 관리</div>
			<div class="regionInfo">

				${hospitalInfo.hname} -
				<img class="timg" src="../images/calendar.png" alt="" />
				: 예약한 인원수 /
				<img class="timg" src="../images/user.png" alt="" />
				: 예약가능한 총 인원수

			</div>

		</div>
		<div class="dateregion">

			<c:forEach var="date" items="${date15 }" varStatus="status">

				<div class="oneday">

					<div class="daay">${date}(${day15[status.index]})</div>
					<div class="tiiime">

						<c:forEach var="stime" items="${startTimes }" varStatus="tst">
							<c:set var="keys" value="${date}${stime }" />

							<div class="timebutton"
								onclick="setMember(this, '${date}', '${stime }', '${countReserve[keys]}')">

								<div class="btn_timeregion">

									<div>${stime}</div>
									<div>~ ${endTimes[tst.index]}</div>

								</div>
								<div class="btn_memregion">

									<div>
										<img class="timg" src="../images/calendar.png" alt="" />
										${countReserve[keys]}명
									</div>
									<div id="btn_${date}${stime}">
										<img class="timg" src="../images/user.png" alt="" />
										${totalReserve[keys]}명
									</div>

								</div>

							</div>

						</c:forEach>

					</div>

				</div>

			</c:forEach>

		</div>

	</div>





	<!-- 모달창내용 -->
	<div class="back_modal"></div>

	<div class="rev_modal">

		<div class="modal_content">

			<div class="modal_head">

				<div class="modal_head_logo">
					<img src="../images/Star1.png" alt="로고" />
					MediCovid
				</div>
				<div class="modal_head_exit">
					<button type="button" id="closeHospitalRegistrationForm">
						<img src="../images/CloseButton.png" onclick="modalclose()"
							alt="닫기" />
					</button>
				</div>

			</div>
			<div class="mini_body">

				<div class="mini_title">예약 가능 인원수 변경</div>
				<div class="mini_date" id="mini_date"></div>
				<div class="mini_revNum" id="mini_revNum"></div>
				<div class="mini_permember">

					<div class="min_pmem_title">예약가능인원</div>
					<div class="min_pmen_select">

						<select name="num" id="num">

							<option id="opt12" value="12">12</option>
							<option id="opt11" value="11">11</option>
							<option id="opt10" value="10">10</option>
							<option id="opt9" value="9">9</option>
							<option id="opt8" value="8">8</option>
							<option id="opt7" value="7">7</option>
							<option id="opt6" value="6">6</option>
							<option id="opt5" value="5">5</option>
							<option id="opt4" value="4">4</option>
							<option id="opt3" value="3">3</option>
							<option id="opt2" value="2">2</option>
							<option id="opt1" value="1">1</option>
							<option id="opt0" value="0">0</option>

						</select>


					</div>


				</div>
				<div class="mini_buttons">

					<input type="hidden" id="hiddenforid" name="" />
					<input type="hidden" id="hiddenfordate" name="" />
					<input type="hidden" id="hiddenfortime" name="" />
					<input type="hidden" id="hiddenhopnum" name=""
						value="${ hospitalInfo.hno}" />

					<input type="button"
						onclick="confirm(hiddenforid.value, hiddenfordate.value, hiddenfortime.value, hiddenhopnum.value)"
						value="적용" />

				</div>
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