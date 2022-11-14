
$(function() {
	// 헤더의 채팅 버튼 클릭해서 채팅창 뜨면 소켓 커넥션 생성되게
	// 관리자는 항상 유저네임 : admin 으로 소켓 커넥션 생성
	connect("admin");

	// 2초마다 채팅룸 목록 불러오기
	setInterval(function() {
		$.ajax({
			method: "GET",
			url: "/chat",
			dataType: "json",
			success: function(result) {
				var str = "";

				$(".chatRoomList").empty();

				$.each(result, function(key, value) {

					str += "<div class='chatRoom'>";
					str += "<p>" + value.sender + "</p>";
					str += "<p>" + value.content + "</p>";
					
					if (value.status > 0) {
						str += "<span class='badge'>" + value.status + "</span>";
					}
					
					str += "</div>";
				});

				$(".chatRoomList").html(str);
			}
		});
	}, 2000);

	// 채팅룸 클릭하면 해당룸 subscribe
	$(".chatRoomList").on("click", ".chatRoom", function() {

		$(".chatRoom").removeClass("selectedRoom");
		$(this).addClass("selectedRoom");

		$(".badge").show();
		$(this).children("span").hide();

		username = $(this).children("p")[0].innerText;

		subscribe(username);

		// 입장한 채팅방에 여태까지 쌓인 채팅목록 불러옴
		$.ajax({
			method: "GET",
			url: "/chat/" + username,
			dataType: "json",
			success: function(result) {
				var str = "";
				const chatbody = $(".chatBody");

				chatbody.empty();

				$.each(result, function(key, value) {
					// 상담원이 들어가 있는 채팅방 헤더에 문의한 사용자명 표시
					if (value.sender == "admin") {
						$(".userName").text(value.receiver);
					} else {
						$(".userName").text(value.sender);
					}

					if (value.receiver == "admin") {
						str = "<p class='received'>";
						str += "<span class='msg'>" + value.content + "</span>";
						str += "<span class='msgTime'>" + value.chatTime + "</span>";
						str += "</p>";

						chatbody.append(str);
						chatbody.scrollTop(chatbody[0].scrollHeight);
					} else {
						str = "<p class='sent'>";
						str += "<span class='msgTime'>" + value.chatTime + "</span>";
						str += "<span class='msg'>" + value.content + "</span>";
						str += "</p>";

						chatbody.append(str);
						chatbody.scrollTop(chatbody[0].scrollHeight);
					}
				});
			}
		});

		// 관리자가 채팅방에 있으면 status 바로 0으로 변경
		setInterval(function() {
			$.ajax({
				method: "PUT",
				url: "/chat/" + username,
				dataType: "json"
			});
		});


	});


	// 메세지는 현재 관리자가 있는 방의 사용자에게 보냄
	$("#sendMsg").on("keyup", function(e) {
		if (e.keyCode == 13) {
			var messageInput = $("#sendMsg");
			const now = new Date();

			var chatMessage = {
				sender: "admin",
				receiver: username,
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
var username;

// 소켓 커넥션 생성
function connect(admin) {
	var socket = new SockJS('/ws');

	stompClient = Stomp.over(socket);

	stompClient.connect({ username: admin }, function() {
		console.log("Web socket is connected");
	});
}

// 구독 & 구독한 엔드포인트로 오는 메세지 출력
function subscribe(username) {

	stompClient.unsubscribe("chatRooms");

	stompClient.subscribe("/room/" + username, function(message) {
		const msg = JSON.parse(message.body);
		let str = "";
		const chatbody = $(".chatBody");

		if (msg.receiver == "admin") {
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

	}, { id: "chatRooms" });

}
