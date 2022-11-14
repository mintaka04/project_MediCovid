<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<table class="table2">
	<tr>
		<th width="8%" id="th_b">예약번호</th>
		<th width="10%">날짜</th>
		<th width = "8%">시간</th>
		<th width = "12%">이름</th>
		<th width = "12%">연락처</th>
		<th width = "50%">특이사항</th>
	</tr>

	<c:forEach var="dto" items="${dateList }">
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