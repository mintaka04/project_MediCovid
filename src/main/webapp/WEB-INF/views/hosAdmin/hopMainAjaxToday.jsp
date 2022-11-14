<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="name1AndButtons">

	<div class="name1">일일 예약 관리</div>

	<div class="daysButtons">
		<button class="btns" id="btnbefore" onclick="dateMinus()">이전날</button>
		<div id="ddate">${dateresult }</div>
		<button class="btns" id="btnafter" onclick="datePlus()">다음날</button>
	</div>

</div>



<div class="datelistresult" id="tb1">

	<!-- 오늘 기준으로 며칠 증가되었는지 나타내기 위함 -->
	<input type="hidden" id="datehidden" value="0" />


	<table class="table1">
		<tr>
			<th width="8%" id="th_b">예약번호</th>
			<th width = "8%">시간</th>
			<th width = "12%">이름</th>
			<th width = "12%">연락처</th>
			<th width = "50%">특이사항</th>
			<th width = "10%">내방확인</th>
		</tr>

		<c:forEach items="${dateList }" var="data">
			<tr>
				<td><div class="td_1">${data.rno}</div></td>
				<td><div class="td_2">${data.rtime}</div></td>
				<td><div class="td_3">${data.rname}</div></td>
				<td><div class="td_4">${data.rtel}</div></td>
				<td><div class="td_5">${data.rremark}</div></td>
				<c:choose>
					<c:when test="${data.rstatus == 3}">
						<td><div class="td_6"><input type="button" class="visitconf"
							id="btn${data.rno }" onclick="buttonToggle(${data.rno});"
							value="내방취소" /></div></td>
					</c:when>
					<c:otherwise>
						<td><div class="td_6"><input type="button" class="visitconf"
							id="btn${data.rno }" onclick="buttonToggle(${data.rno});"
							value="내방확인" /></div></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>

	</table>
</div>