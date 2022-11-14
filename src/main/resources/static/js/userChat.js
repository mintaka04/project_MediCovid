
$(function() {

	let username;
	const now = new Date();
	let chattime = (now.getHours() < 10 ? '0' + now.getHours() : now.getHours()) + ":" + (now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes());
	const answerAboutReservation = "예약 방법 : 예약 원하시는 병원명 검색 후 해당 병원 클릭 -> 병원 정보 페이지 중간 예약하기 버튼 클릭 -> 원하는 예약 날짜, 시간 선택 후 예약자명, 연락처, 특이사항 입력 후 예약 등록 버튼 클릭 -> 예약 완료!";
	const answerAboutUserInfo = "휴대폰 번호가 변경되었어요 / 비밀번호를 변경하고 싶어요 : 메인페이지 상단 마이페이지 클릭 - 마이페이지 상단 내 정보 수정 클릭 -> 비밀번호, 연락처 수정";

	$(".bot1 .msgTime, .bot2 .msgTime").text(chattime);

	// 예약관련 문의 버튼 클릭 자동 답변
	$(".chatBody").on("click", ".askAboutReservation", function() {
		const chatbody = $(".chatBody");
		let str = "";

		str = "<p class='sent'>";
		str += "<span class='msgTime'>" + chattime + "</span>";
		str += "<span class='msg'>예약관련 문의</span>";
		str += "</p>";

		chatbody.append(str);
		chatbody.scrollTop(chatbody[0].scrollHeight);

		// 채팅같은 느낌 주려고 timeout 설정함
		setTimeout(function() {
			str = "<p class='received'>";
			str += "<span class='msg'>" + answerAboutReservation + "</span>";
			str += "<span class='msgTime'>" + chattime + "</span>";
			str += "</p>";

			chatbody.append(str);
			chatbody.scrollTop(chatbody[0].scrollHeight);
		}, 1000);

		// 채팅같은 느낌 주려고 timeout 설정함
		setTimeout(function() {
			str = "<p class='received bot2'>"
			str += "<span class='msg'>"
			str += "<input type='button' value='예약관련 문의' class='askAboutReservation'><br>"
			str += "<input type='button' value='회원정보 문의' class='askAboutUserInfo'><br>"
			
			if ($(".startChatBtn").attr("disabled") == "disabled") {
				str += "<input type='button' value='상담원과 채팅' class='startChatBtn' disabled>"
			} else {
				str += "<input type='button' value='상담원과 채팅' class='startChatBtn'>"
			}
			
			str += "</span>"
			str += "<span class='msgTime'>" + chattime + "</span>"
			str += "</p>"

			chatbody.append(str);
			chatbody.scrollTop(chatbody[0].scrollHeight);
		}, 1500);

	});

	// 회원정보 문의 버튼 클릭 자동 답변
	$(".chatBody").on("click", ".askAboutUserInfo", function() {
		const chatbody = $(".chatBody");
		let str = "";

		str = "<p class='sent'>";
		str += "<span class='msgTime'>" + chattime + "</span>";
		str += "<span class='msg'>회원정보 문의</span>";
		str += "</p>";

		chatbody.append(str);
		chatbody.scrollTop(chatbody[0].scrollHeight);
		
		// 채팅같은 느낌 주려고 timeout 설정함
		setTimeout(function() {
			str = "<p class='received'>";
			str += "<span class='msg'>" + answerAboutUserInfo + "</span>";
			str += "<span class='msgTime'>" + chattime + "</span>";
			str += "</p>";

			chatbody.append(str);
			chatbody.scrollTop(chatbody[0].scrollHeight);
		}, 1000);
		
		// 채팅같은 느낌 주려고 timeout 설정함
		setTimeout(function() {
			str = "<p class='received bot2'>"
			str += "<span class='msg'>"
			str += "<input type='button' value='예약관련 문의' class='askAboutReservation'><br>"
			str += "<input type='button' value='회원정보 문의' class='askAboutUserInfo'><br>"
			
			if ($(".startChatBtn").attr("disabled") == "disabled") {
				str += "<input type='button' value='상담원과 채팅' class='startChatBtn' disabled>"
			} else {
				str += "<input type='button' value='상담원과 채팅' class='startChatBtn'>"
			}

			str += "</span>"
			str += "<span class='msgTime'>" + chattime + "</span>"
			str += "</p>"

			chatbody.append(str);
			chatbody.scrollTop(chatbody[0].scrollHeight);
		}, 1500);

	});

	// 상담원과 채팅 버튼 클릭하면  
	$(".chatBody").on("click", ".startChatBtn", function() {
		// 유니크한 랜덤 문자열 생성하여 유저 네임으로 지정
		let randomStr = Math.random().toString(36).substring(2, 12);
		let today = new Date();
		username = randomStr + today.getTime();
		console.log(username);

		// 생성된 유저네임으로 소켓 커넥션 생성
		connect(username);
		// 채팅 입력 창 활성화
		$("#sendMsg").prop("disabled", false);
		$("#sendMsg").focus();
		// 상담원과 채팅 버튼 비활성화
		$(".startChatBtn").prop("disabled", true);

		const chatbody = $(".chatBody");
		let str = "";

		str = "<p class='received'>";
		str += "<span class='msg'>무엇을 도와드릴까요?</span>";
		str += "<span class='msgTime'>" + chattime + "</span>";
		str += "</p>";

		chatbody.append(str);
		chatbody.scrollTop(chatbody[0].scrollHeight);
	});

	// 채팅 입력창에서 엔터치면 관리자에게 채팅 보내짐
	// 사용자가 메세지 보낼 수 있는 대상은 항상 admin 임
	$("#sendMsg").on("keyup", function(e) {
		if (e.keyCode == 13) {
			let messageInput = $("#sendMsg");

			var chatMessage = {
				sender: username,
				receiver: "admin",
				content: messageInput.val(),
				chatTime: (now.getHours() < 10 ? '0' + now.getHours() : now.getHours()) + ":" + (now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes())
			};

			stompClient.send("/app/hello", {}, JSON.stringify(chatMessage));

			messageInput.val("");
		}
		e.preventDefault();
	});
	
});

var stompClient = null;

function connect(username) {
	var socket = new SockJS('/ws');

	stompClient = Stomp.over(socket);
	stompClient.connect({ username: username }, function() {
		console.log("Web socket is connected");

		// 사용자가 구독한 엔드포인트(/room/{username})로 메세지가 들어오면
		// .chatBody에 메세지 출력됨
		stompClient.subscribe("/room/" + username, function(message) {
			const msg = JSON.parse(message.body);
			const chatbody = $(".chatBody");

			let str = "";

			if (msg.sender == "admin") {
				str = "<p class='received'>";
				str += "<span class='msg'>" + msg.content + "</span>";
				str += "<span class='msgTime'>" + msg.chatTime + "</span>";
				str += "</p>";

				chatbody.append(str);
				chatbody.scrollTop(chatbody[0].scrollHeight);
			} else {
				str = "<p class='sent'>";
				str += "<span class='msgTime'>" + msg.chatTime + "</span>";
				str += "<span class='msg'>" + msg.content + "</span>";
				str += "</p>";

				chatbody.append(str);
				chatbody.scrollTop(chatbody[0].scrollHeight);
			}
		});
	});
}



