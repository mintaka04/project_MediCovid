<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MediCovid - 관리자 채팅</title>
<link rel="stylesheet" href="/css/adminChat.css">
<link rel="icon" href="../images/Star1.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script type="text/javascript" src="/js/adminChat.js"></script>
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

	<div class="content">
		<div class="chatRoomList">

			<c:forEach var="dto" items="${list }">
				<div class="chatRoom">
					<p>${dto.sender }</p>
					<p>${dto.content }</p>
					<c:if test="${dto.status != 0}">
						<span class="badge">${dto.status }</span>
					</c:if>
				</div>
			</c:forEach>

		</div>

		<div class="chat">
			<div class="chatHeader">
				<div>
					<img src="../images/information.png" alt="채팅방 정보">
				</div>
				<div>
					<div class="userName"></div>
				</div>
			</div>
			<div class="chatBody"></div>
			<div class="chatFooter">
				<input type="text" name="sendMsg" id="sendMsg"
					placeholder="내용을 입력하세요">
			</div>
		</div>
	</div>

	<div class="footer"></div>
	</div>
</body>
</html>