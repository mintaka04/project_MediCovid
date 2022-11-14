<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/notice.css">
<link rel="icon" href="../images/Star1.png">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/notice.js"></script>
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
		</div>
	</div>

	<!-- 모달 -->
	<div class="block"></div>
	<div class="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5>
						<img src="../images/Star1.png" alt="로고" />
						MediCovid
					</h5>
					<button type="button" id="closeBtn">
						<img src="../images/CloseButton.png" alt="닫기" />
					</button>
				</div>
				<form action="">
					<input type="hidden" name="_method" value="" />
					<div class="modal-body">
						<h5 class="modal-title"></h5>
						<div class="titleDiv">
							<span>제목</span>
							<input type="text" class="" name="title" value="">
						</div>
						<div class="contentDiv">
							<textarea name="content"></textarea>
						</div>

					</div>

					<div class="modal-footer">
						<c:if test="${session.role == 'ROLE_ADMIN' }">
							<input type='submit' class="writeOkBtn" value='작성 완료'>
							<input type='button' class="editBtn" value='수정하기'>
							<input type="button" class="deleteOkBtn" value="삭제" />
							<input type="button" class="editOkBtn" value="수정" />
						</c:if>
					</div>

				</form>
			</div>
		</div>
	</div>

	<div class="content">
		<h3>공지사항</h3>
		<div>
			<c:if test="${session.role == 'ROLE_ADMIN' }">
				<input type="button" class="writeBtn" value="글 작성">
			</c:if>
		</div>
		<table>
			<tr>
				<th>순번</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:forEach var="bdto" items="${list }">
				<tr class="clickable">
					<td>${bdto.bno }</td>
					<td>${bdto.title }</td>
					<td>${bdto.writer }</td>
					<td>${bdto.regdate }</td>
				</tr>
			</c:forEach>

		</table>

		<!-- 페이징 -->
		<div class="paginationDiv">
			<ul class="pagination">
				<c:if test="${dto.prev }">
					<li class='arrow prev'><a
							href="board?cp=${dto.currentPage-10 }">
							<img src='../images/left.png' alt='10페이지 이전으로' />
						</a></li>
				</c:if>

				<c:forEach var="i" begin="${dto.startPage }" end="${dto.endPage }">
					<c:if test="${dto.currentPage == i}">
						<li class='pageButton currentPage'><a href="board?cp=${i }">${i }</a></li>
					</c:if>
					<c:if test="${dto.currentPage != i}">
						<li class='pageButton'><a href="board?cp=${i }">${i }</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${dto.next }">
					<li class='arrow next'><a
							href="board?cp=${dto.currentPage+10 }">
							<img src='../images/right.png' alt='10페이지 뒤로' />
						</a></li>
				</c:if>
			</ul>
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
</body>
</html>