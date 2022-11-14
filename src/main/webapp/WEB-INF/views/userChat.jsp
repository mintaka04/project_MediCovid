<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MediCovid - 고객센터 채팅</title>
<link rel="stylesheet" href="/css/userChat.css">
<link rel="icon" href="../images/Star1.png" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script type="text/javascript" src="/js/userChat.js"></script>
</head>
<body>
	<!-- 사용자 채팅창 -->
	<div class="modal" id="">
		<div class="modal-header">
			<h5>
				<img src="../images/Star1.png" alt="로고" />
				MediCovid
			</h5>
		</div>
		<div class="modal-body">
			<div class="chatHeader">
				<div>
					<img src="../images/information.png" alt="채팅방 정보">
				</div>
				<div>챗봇/고객센터</div>
			</div>
			<div class="chatBody">
				<p class="received bot1">
					<span class="msg">상담창에 오신것을 환영합니다!</span>
					<span class="msgTime"></span>
				</p>
				<p class="received bot2">
					<span class="msg">
						<input type="button" value="예약관련 문의" class="askAboutReservation">
						<br>
						<input type="button" value="회원정보 문의" class="askAboutUserInfo">
						<br>
						<input type="button" value="상담원과 채팅" class="startChatBtn">
					</span>
					<span class="msgTime"></span>
				</p>
			</div>
			<div class="chatFooter">
				<input type="text" name="sendMsg" id="sendMsg"
					placeholder="내용을 입력하세요." disabled>
			</div>
		</div>

	</div>
</body>
</html>