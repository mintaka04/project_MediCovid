<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="/css/loginForm.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	$(function() {

		var $uid = $("#uid");
		var $msg = $("#msg");

		/* 전화번호로 이메일 찾기  */
		$(".userpnumButton").on("click", function() {
			var params = {
				utel : $("#utel")[0].value
			};
			console.dir($("#uid"));
			if ($("#utel")[0].value.length == 0) {
				console.log("전화번호형식이 틀렸습니다")
			} else {
				$.ajax({
					type : "POST",
					url : "/findId",
					data : JSON.stringify(params), // 
					contentType : 'application/json',
					success : function(response) { //성공했을경우 컨트롤로에서 데이터를 넘겨주는것 response
						if (response === "no") {
							alert("실명인증 실패했습니다")
							utel.value = "";
						} else {
							$("#uid")[0].value = response;

						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("실명인증 실패했습니다")
						utel.value = "";
					}
				});
			}

			$(".useremailButton").click(function() {
				// console.log("test")
				$.ajax({
					type : "POST",
					url : "/findMemberPwd",
					data : {
						"uid" : $uid.val()
					},
					succes : function(data) {
						if (data == "") {
							console.log("없음")

						} else {
							console.log("data :" + data)

						}

					}

				})

			})

		})

	})
	function loginSubmit() {
		var userChk = $("#user").is(':checked');
		var hospChk = $("#hosp").is(':checked');

		var $user = $("#useruid");

		var $username = $("#username");
		var $password = $("#password");

		if (userChk) {
			var username = $user.val() + " user"
			$username.val(username);
			//console.log("username : "+$username.val())
			return true;
		} else if (hospChk) {
			var username = $user.val() + " hosp"
			$username.val(username);
			return true;
		} else {
			return false;
		}

		return false;
	}
</script>
<link rel="icon" href="../images/Star1.png">
</head>
<body>
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="main">
					<img src="../images/Star1.png" alt="로고" />
					MediCovid
				</a>
			</div>
			<div class="pagego">
				<a href="main">첫 화면으로</a>
				<a href="search/hospitals?category=1">호흡기 전담클리닉</a>
				<a href="search/hospitals?category=2">재택치료 외래센터</a>
				<a href="search/hospitals?category=3">전화상담 병의원</a>
			</div>
		</div>
	</div>

	<div class="main">

		<div class="imgbox">
				<img src="../images/login.png" alt="login" />
		</div>


		<div class="formbox">
			<form action="/login" method="POST" id="frm"
				onsubmit="return loginSubmit()">
				<div class="loginLogo">
					<img src="../images/Star2.png" alt="로고" />
					<span>MediCovid</span>
				</div>

				<div class="useremail">
					<input type="text" name="useruid" id="useruid"
						placeholder="아이디(이메일)를 입력해주세요" />
					<input type="hidden" class="form-control" id="username"
						name="username">

					<br />
					<span id="msg"></span>
				</div>
				<br />
				<div class="userpw">
					<input type="password" name="password" id="userupw"
						placeholder="비밀번호를 입력해주세요" />
					<br />
					<span id="upw_check"></span>
				</div>
				<br />
				<br />

				<div class="radiobox">
					<div class="btn-group">
						<input type="radio" class="btn-check" name="btnradio" id="user"
							autocomplete="off">
						<label class="btnradiocheck" for="member">일반 회원</label>
					</div>
					<div class="btn-grouptwo">
						<input type="radio" class="btn-check" name="btnradio" id="hosp"
							autocomplete="off">
						<label class="btnradiocheck" for="company">병원</label>
					</div>
				</div>
				<input type="submit" id="btn1" value="로그인" />

			</form>



			<a href="/oauth2/authorization/kakao">
				<img class="kakao" src="../images/kakao.png" alt="로고" />
			</a>

			<a href="/oauth2/authorization/naver">
				<img class="naver" src="../images/naver.png" alt="로고" />
			</a>
			<br />
			<input type="checkbox" name="" id="popup">
			<label for="popup"><p class="textfont">아이디 또는 비밀번호를
					잊어버렸어요</p></label>

			<div>
				<div class="modal">
					<label for="popup"><img src="../images/CloseButton.png"
							alt="로고" /></label>
					<div class="modal-header">
						<h5>
							<img src="../images/Star1.png" alt="로고" />
							MediCovid
						</h5>
						<div class="modaltitle">
							<p>아이디/비밀번호찾기</p>
						</div>
						<div class="modallist">
							<ul>
								<li>아이디는 연락처 인증을 통해서 확인할수 있습니다.</li>
								<li>가입하실때 입력한 이메일로 임시 비밀번호가 전송됩니다.</li>
								<li>임시 비밀번호로 로그인후 비밀번호를 재설정해주세요.</li>
							</ul>
						</div>
						<div class="modaltel">
							<input type="text" name="utel" id="utel"
								placeholder="연락처('-' 없이 입력해주세요) " />
							<input class="userpnumButton" type="button" value="실명인증" />
							<span id="utel_check"></span>
						</div>
						<div class="modalemail">
							<input type="email" name="uid" id="uid"
								placeholder="이메일(example@example.com)" />
							<input class="useremailButton" type="button" value="임시 PW 전송" />
							<br />
							<span id="msg"></span>
						</div>
					</div>
				</div>
				<label for="popup"></label>
			</div>
			<br />
			<a href="/register" class="textfonttwo">지금 바로 회원가입하기 </a>
		</div>
	</div>



	<div class="footer">
		<div class="container3">
			<div class="logo">
				<img src="../images/Star1.png" alt="로고" />
				<span>MediCovid</span>
			</div>
			<div class="link">
				<a href="#">이용약관</a>
				<a href="#">개인정보 취급방침</a>

				<a href="../board">공지사항</a>
				<a href="#">사이트맵</a>



				<a href="#">병원가입 안내</a>
			</div>
			<div class="etcInfo">
				<p>서울 종로구 율곡로10길 105 디아망 4층</p>
				<p>
					(주)MediCovid | 대표자 : 황경화 | 사업자등록번호 : 111-11-11111 | 통신판매신고 :
					제2022-011호
					<br />
					개인정보관리자 : 김민식(kms@medicovid.com)
				</p>
				<p>COPYRIGHT 2022 MEDICOVID ALL RIGHTS RESERVED</p>
			</div>
		</div>
	</div>
</body>
</html>