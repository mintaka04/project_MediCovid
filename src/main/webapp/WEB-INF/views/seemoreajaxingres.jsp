<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- 진행중인 예약 => 더 보기 버튼 클릭하면 쏴주는 jsp -->

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
			<button class="btn2"
				onclick="reservationcancel('${dto.hname}', '${dto.rdate }', '${dto.rtime }', '${dto.rno }')">예약
				취소</button>
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
</c:forEach>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"
		integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
		crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/myinfoedit.js"></script>