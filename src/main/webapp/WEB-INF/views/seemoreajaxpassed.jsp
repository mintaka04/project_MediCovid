<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 지난 예약 내역 => 더 보기 버튼 클릭하면 쏴주는 jsp -->

<c:forEach var="dto" items="${hospitallist }" varStatus="status">
	<div class="inghospitalinfo">
		<div class="mapapi" id="mapapi${status.index }"></div>
		<input type="hidden" name="hx" class="hx" value="${dto.hx }" /> <input
			type="hidden" name="hy" class="hy" value="${dto.hy }" />
		<div class="hospitalname">
			<p>${dto.hname }</p>
		</div>
		<div class="inquiry">
			<button class="btn1">1:1 문의</button>
			<button class="btn3" onclick="setMember2('${status.index}')">이용 후기</button>
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
			<img class=icon1 src="../images/breathHospital.png" alt="호흡기 전담클리닉" />
			<img class=icon2 src="../images/telHospital.png" alt="전화상담 병의원" /> <img
				class=icon3 src="../images/homeHospital.png" alt="재택치료 외래센터" />
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
							<img id="image1" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image2" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image3" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image4" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image5" src="../images/StarEmpty.png" alt="별점" />
						</div>
					</div>
					<div class="nurse_point">
						간호사 친절성
						<div class="star2">
							<img id="image6" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image7" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image8" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image9" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image10" src="../images/StarEmpty.png" alt="별점" />
						</div>
					</div>
					<div class="facilities_point">
						병원 내부 환경
						<div class="star3">
							<img id="image11" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image12" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image13" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image14" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image15" src="../images/StarEmpty.png" alt="별점" />
						</div>
					</div>
					<div class="wait_point">
						대기시간 만족도
						<div class="star4">
							<img id="image16" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image17" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image18" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image19" src="../images/StarEmpty.png" alt="별점" /> <img
								id="image20" src="../images/StarEmpty.png" alt="별점" />
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"
		integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
		crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myinfoedit.js"></script>