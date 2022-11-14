<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/myinfo.css" />
<script
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<spring:eval expression="@environment.getProperty('passreservation.appkey')" />"></script>
<title>MediCovid</title>
</head>
<body>
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="main">
					<img src="../images/Star1.png" alt="로고">
					MediCovid
				</a>
			</div>
			<div class="chatAndLogout">
				<a href="#" onclick="setMember()">내 정보 수정</a>
				<a href="logout">로그아웃</a>
			</div>
		</div>
	</div>
	<div class="content">
		<div class="reservation">
			<div class="IngReservation">
				<a class="ing_a" href="mypage">
					<img id="ingres" src="../images/ing.png" alt="진행중인 예약" />
					진행중인 예약
				</a>
			</div>
			<div class="PassedReservation">
				<a class="ing_a" href="passreservation">
					<img id="passedres" src="../images/passed.png" alt="지난 예약 내역" />
					지난 예약 내역
				</a>
			</div>
		</div>

		<!--  list 형태  -->
		<div class="ajaxlist" id="ajaxlist">


			<c:forEach var="dto" items="${hospital }" varStatus="status">
				<div class="inghospitalinfo">
					<div class="mapapi" id="mapapi${status.index }"></div>
					<input type="hidden" name="hx" class="hx" value="${dto.hx }" />
					<input type="hidden" name="hy" class="hy" value="${dto.hy }" />
					<div class="hospitalname">
						<p>${dto.hname }</p>
					</div>
					<div class="inquiry">
						<button class="btn1">1:1 문의</button>
						<button class="btn3" onclick="setMember2('${status.index}')">이용
							후기</button>
					</div>
					<div class="hospitalinfo">
						<div class="hospitaladdr">
							<p>${dto.haddress }</p>
						</div>
						<div class="reservationno">
							<p>예약번호 : ${dto.rno }</p>
						</div>
						<div class="reservationdate">
							<p>예약일시 : ${dto.rdate } ${dto.rtime }</p>
						</div>
					</div>
					<div class="icon">
						<img class=icon1 src="../images/respiratory.png" alt="호흡기 전담클리닉" />
						<img class=icon2 src="../images/callconsult.png" alt="전화상담 병의원" />
						<img class=icon3 src="../images/homecure.png" alt="재택치료 외래센터" />
					</div>
					<!-- <div class="divideline">
				
			</div> -->
				</div>
				<!-- 이용후기 모달 -->
				<div class="back_modal2"></div>

				<div class="rev_modal2" id="${status.index }">

					<div class="modal_content">

						<div class="mini_navbar">

							<div class="minibar_logo">
								<img id="minibar_img" src="../images/Star1.png" alt="로고">
								MediCovid
							</div>
							<div class="minibar_close">
								<a href="#" onclick="modalclose2('${status.index}')">X</a>
							</div>

						</div>
						<div class="mini_body">

							<div class="mini_title2">이용 후기</div>
							<div class="hospital_name">${dto.hname }</div>
							<div class="passedreservation_info">
								<div class="hospital_addr">${dto.haddress }</div>
								<div class="reservation_no">
									예약번호 <label for="rno">${dto.rno }</label>
								</div>
								<div class="reservation_date">
									예약일시 <label for="rdate">${dto.rdate } ${dto.rtime }</label>
								</div>
							</div>

							<div class="star_point">
								<div class="doctor_point">
									의사 진료 만족도
									<div class="star1">
										<img id="image1" src="../images/StarEmpty.png" alt="별점" />
										<img id="image2" src="../images/StarEmpty.png" alt="별점" />
										<img id="image3" src="../images/StarEmpty.png" alt="별점" />
										<img id="image4" src="../images/StarEmpty.png" alt="별점" />
										<img id="image5" src="../images/StarEmpty.png" alt="별점" />
									</div>
								</div>
								<div class="nurse_point">
									간호사 친절성
									<div class="star2">
										<img id="image6" src="../images/StarEmpty.png" alt="별점" />
										<img id="image7" src="../images/StarEmpty.png" alt="별점" />
										<img id="image8" src="../images/StarEmpty.png" alt="별점" />
										<img id="image9" src="../images/StarEmpty.png" alt="별점" />
										<img id="image10" src="../images/StarEmpty.png" alt="별점" />
									</div>
								</div>
								<div class="facilities_point">
									병원 내부 환경
									<div class="star3">
										<img id="image11" src="../images/StarEmpty.png" alt="별점" />
										<img id="image12" src="../images/StarEmpty.png" alt="별점" />
										<img id="image13" src="../images/StarEmpty.png" alt="별점" />
										<img id="image14" src="../images/StarEmpty.png" alt="별점" />
										<img id="image15" src="../images/StarEmpty.png" alt="별점" />
									</div>
								</div>
								<div class="wait_point">
									대기시간 만족도
									<div class="star4">
										<img id="image16" src="../images/StarEmpty.png" alt="별점" />
										<img id="image17" src="../images/StarEmpty.png" alt="별점" />
										<img id="image18" src="../images/StarEmpty.png" alt="별점" />
										<img id="image19" src="../images/StarEmpty.png" alt="별점" />
										<img id="image20" src="../images/StarEmpty.png" alt="별점" />
									</div>
								</div>
							</div>
							<div class="review_button">
								<input type="button" id="button3" onclick="reviewsubmit()"
									value="후기 등록" />
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<input type="hidden" name="" id="viewall" value="${listlength }" />
		<c:set var="listlength" value="${listlength }"></c:set>
		<c:if test="${listlength != 'false' }">
			<div class="moresee" onclick="passedseeplus()">
				<img id="downside" src="../images/LeftArrow.png" alt="아래 꺽쇠" />
				<label for="see">더 보기</label>
			</div>

		</c:if>
	</div>
	<input type="hidden" name="" id="uno" value="${model.uno }" />

	<!-- 내 정보 수정 모달 -->
	<div class="back_modal"></div>

	<div class="rev_modal">

		<div class="modal_content">

			<div class="mini_navbar">

				<div class="minibar_logo">
					<img id="minibar_img" src="../images/Star1.png" alt="로고">
					MediCovid
				</div>
				<div class="minibar_close">
					<a href="#" onclick="modalclose()">X</a>
				</div>

			</div>
			<div class="mini_body">

				<div class="mini_title">내 정보 수정</div>
				<div class="id" id="myid">
					내 아이디 <label for="userid">${model.uid}</label>
				</div>
				<div class="notice">
					<label for="notice1"> *기존 비밀번호는 필수 입력입니다. </label>
					<br />
					<label for="notice2"> *EX) 연락처만 변경 시 기존비밀번호와 새 연락처 입력 </label>
				</div>
				<div class="user_info1">
					<input type="text" name="" id="existedPw"
						placeholder=" 기존 비밀번호를 입력해주세요(필수)" />
					<div class="check" id="checkexistPw"></div>
				</div>
				<div class="user_info2">
					<input type="text" name="" id="newPw"
						placeholder=" 새 비밀번호(문자, 숫자, 특수문자 혼합, 8~20자)" />
					<div class="check" id="checkPw"></div>
				</div>
				<div class="user_info3">
					<input type="text" name="newPw2" id="newPw2"
						placeholder=" 새 비밀번호 확인" />
					<div class="check" id="checknewPw"></div>
				</div>
				<div class="user_info4">
					<input type="text" name="newPhone" id="newPhone"
						placeholder=" 새 연락처('-'없이 인력해주세요)" />
					<div class="check" id="checknewPhone"></div>
				</div>

				<div class="drop_button">
					<input type="button" id="button1" onclick="dropuser()"
						value="회원 탈퇴" />
					<input type="button" id="button2" onclick="modifyuserinfo()"
						value="수정 완료" />
				</div>
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
				<a href="board">공지사항</a>
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

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"
		integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
		crossorigin="anonymous"></script>

	<script type="text/javascript" src="/js/myinfoedit.js"></script>
	<%-- 	<div id="div1st">
		<table>
			<tr>
				<th>병원이름</th>
				<th>병원주소</th>
				<th>예약번호</th>
				<th>예약일시</th>
			</tr>
				<td>${dto.hname }</td>
				<td>${dto.haddress }</td>
				<td></td>
				<td></td>
		</table>
	</div> --%>
</body>
</html>