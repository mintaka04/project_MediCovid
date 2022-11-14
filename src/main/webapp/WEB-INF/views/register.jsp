<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MediCovid</title>

<link rel="stylesheet" href="/css/register.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
	$(function() {
		var $uname = $("#uname");
		var $uid = $("#uid");
		var $upw = $("#upw");
		var $upwcheck = $("#upwcheck");
		var $utel = $("#utel");
		$("#btn1").on("click", checkregform);

		//   모든 공백 체크 정규식
		var empH = /\s/g;
		//  이름 정규식 (한글만사용,2~6자리이름)
		var nameH = /^[가-힣]{2,6}$/;
		//  비밀번호 정규식  (영문, 숫자, 특수문자 혼합하여 8~20자리)
		var pwH = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$/;
		//  이메일 정규식	
		var mailH = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.[a-zA-Z]{2,3}$/;
		//  휴배폰번호 정규식(-생략,01?(3글자),3~4숫자,4숫자)
		var phoneH = /^(010)([0-9]{4})([0-9]{4})$/;

		//1번 내가원하는 위치에 인풋값을 가져온다 
		//2번 가져온 인풋값을 콘솔에 찍어본다
		//3번 keyup 을 활용해서 내가누를떄마다 인풋값이 콘솔에찍히는지
		//4번 keyup",function 내에서 실행될때 스팬에 변화는걸 확인한다

		//이름 유효성검사
		$uname.on("keyup", function() {
			if (!nameH.test($uname.val())) { //틀렸을때 test 함수
				console.log("틀렸다")

				$("#name_check").text("한글만,2~6자리이름만 사용가능합니다");
				$("#name_check").css("color", "red");
			} else {
				console.log("맞았다")
				$("#name_check").text("실명확인 완료되었습니다");
				$("#name_check").css("color", "green");
			}
		})

		var uidChk = false;
		// 1. uid 가 이메일 형식이 아니면 false, 맞으면 true
		// 2. 중복확인 버튼을 눌렀을 때 uidChk = true 일 때,  ajax 실행
		// 3. uidDupChk = true 일 때 회원이 되게

		//이메일 유효성검사
		$uid.on("keyup", function() {

			if (!mailH.test($uid.val())) {
				uidChk = false;
				console.log("false" + " " + uidChk)
				$("#msg").text("이메일형식으로 작성해주세요")
				$("#msg").css("color", "red");
			} else {
				uidChk = true;
				console.log("true" + " " + uidChk)
				$("#msg").text("이메일사용가능")
				$("#msg").css("color", "green")

			}
		})
		var uidemailChk = false;
		$("input[value='중복확인']").on("click", function() { //클릭하면 함수호출
			var $checkID = $("#uid").val();

			var $msg = $("#msg");

			// console.log($checkID);	  
			if (uidChk) {
				$.ajax({
					type : "POST",
					//async:true 비동기화 방식
					url : "/uidCheckid", //찾아갈url

					data : {
						"uid" : $checkID
					}, //파라미터 :uid , 값이 checkId    전달할데이터(보내는것)
					dataType : "text", //전송받을 데이터 
					success : function(data) { //성공했다면 이함수를 실행해   data=성공했을때서버로부터 응답받는값
						console.log("data:" + data);
						if (data == 1) { //ajax 받는data 컨틀로쪽에서는 보내는data 
							uidemailChk = false;
							$("#msg").text("이메일 중복입니다.")
							$("#msg").css("color", "red")
							uid.value = "";
						} else {
							uidemailChk = true;
							$("#msg").text("중복확인 완료!")
							$("#msg").css("color", "green")
						}
					}
				})
			} else {
				{
					console.log("실행되면 안됨");
				}
			}

		})

		// 비밀번호유효성검사
		var upwChk = false;
		$upw.on("keyup", function() {
			if (!pwH.test($upw.val())) {
				console.log("틀렸다")
				upwChk = false;
				$("#upw_check").text("조건에 맞지 않는 조합입니다.")
				$("#upw_check").css("color", "red");

			} else {
				upwChk = true;
				console.log("맞았다")
				$("#upw_check").text("조건에 충족합니다")
				$("#upw_check").css("color", "green");

			}
		})

		var upwChk2 = false;
		// 비밀번호 중복확인
		$upwcheck.on("keyup", function() {

			if ($("#upw").val() != $(this).val()) {
				upwChk2 = false;
				$("#upwcheck_check").text("비밀번호가 일치하지 않습니다")
				$("#upwcheck_check").css("color", "red");

			} else {
				upwChk2 = true;
				$("#upwcheck_check").text("비밀번호가 일치합니다")
				$("#upwcheck_check").css("color", "green");

			}
		})

		var utelChk = false;
		//  휴대번호 인증확인
		$("input[value='실명인증']").on("click", function() {
			if (!phoneH.test($utel.val())) {
				utelChk = false;
				console.log("틀렸다")
				$("#utel_check").text("연락처(번호를 다시입력해주세요)")
				$("#utel_check").css("color", "red");
			} else {
				utelChk = true;
				console.log("맞았다")
				$("#utel_check").text("휴대폰 인증 완료되었습니다")
				$("#utel_check").css("color", "green");
			}
		})

		//폼공백체크
		function checkregform() {

			var uname = $("#uname").val();
			// String uname = ""; =>> if(uname==null)
			// boolean uname = false; ==> if(uname==null)
			var uid = $("#uid").val();
			var upw = $("#upw").val();
			var upwcheck = $("#upwcheck").val();
			var utel = $("#utel").val();

			if (uname == "") { // String
				$("#uname").focus();
				return;
			} else if (uid == "") {
				$("#uid").focus();
				return;
			} else if (uidChk == false) {
				$("#uid").focus();
				return;

			} else if (uidemailChk == false) {
				$("#uid").focus();
				return;
			} else if (upwChk == false) {
				$("#upw").focus();
				return;
			} else if (upwChk2 == false) {
				$("#upwcheck").focus();
				return;
			} else if (utel == "") {
				$("#utel").focus();
				return;
			} else if (utelChk == false) {
				$("#utel").focuso();
			} else if (!$("#chek").is(":checked")) {
				return;
			} else {
				$("#frm").submit();

			}

		}
	})
</script>
<link rel="icon" href="../images/Star1.png">
</head>
<body>
	<div class="header">
		<div class="container">
			<div class="logo">
				<a href="#">
					<img src="../images/Star1.png" alt="로고" />
					MediCovid
				</a>
			</div>
			<div class="pagego">
				<a href="main">첫 화면으로</a>
				<a href="search/hospitals?category=1">호흡기 전담클릭닉</a>
				<a href="search/hospitals?category=2">재택치료 외래센터</a>
				<a href="search/hospitals?category=3">전화상담 병의원</a>
			</div>
			<div class="login">
				<a href="loginForm">로그인</a>
			</div>
		</div>
	</div>

	<div class="main">
		<div class="title">
			<p>회원가입</p>
		</div>
		<div class="titletwo">
			<ul>
				<li>회원가입 절차 없이 소셜미디어 아이디로 간편한 로그인 가능합니다.</li>
				<li>병원등록은 페이지 하단링크를 통해 안내받으실수 있습니다.</li>
			</ul>
		</div>

		<form action="register" name="frm" id="frm" method="post">
			<!--register  -->
			<div class="insert-div">
				<div class="username1">
					<div>
						<input type="text" name="uname" id="uname"
							placeholder="실명을 입력해주세요" />
					</div>
					<span id="name_check"></span>
				</div>

				<div class="useremail">
					<div>
						<input type="email" name="uid" id="uid"
							placeholder="이메일(example@example.com)" />
						<input class="useremailButton" type="button" value="중복확인" />
					</div>
					<span id="msg"></span>
				</div>

				<div class="userpwd">
					<div>
						<input type="password" name="upw" id="upw"
							placeholder="비밀번호(문자, 숫자, 특수문자 혼합, 8-12자)" />
					</div>
					<span id="upw_check"></span>
				</div>

				<div class="userpwd2">
					<div>
						<input type="password" name="upwcheck" id="upwcheck"
							placeholder=" 비밀번호 확인" />
					</div>
					<span id="upwcheck_check"></span>
				</div>

				<div class="userpnum">
					<div>
						<input type="text" name="utel" id="utel"
							placeholder="연락처('-' 없이 입력해주세요) " />
						<input class="userpnumButton" type="button" value="실명인증" />
						<span id="utel_check"></span>
					</div>
				</div>
				<div class="textbox">
					<div class="text">
						<textarea class="text2" rows="10" cols="51">
  [개인정보취급방침]
 
의료서비스를 이용하는 여러분의 편의를 위해 최선을 다하는 ㈜세명(이하 “당사”)입니다.

당사에서는 개인정보취급방침을 통해 회원 여러분의 정보를 어떻게 이용하고 관리하며 개인 정보 보호가 어떻게 이루어 지는지에 대하여 알려 드리고 있습니다.

당사는 [정보통신망 이용촉진 및 정보보호등에 관한 법률]을 준수하여 개인 정보를 보호하는데 최선을 다하고 있습니다.

당사의 개인정보취급방침은 관련 법률 및 정부의 지침 변경이나 당사 내부 방침의 변경으로 인하여 변경될 수 있습니다. 개인정보취급방침이 개정된 경우에는 즉시 공지사항에 공지하고 변경된 사항에 대하여 회원들이 쉽게 알아 볼 수 있도록 홈페이지에 게시하며 이메일수신에 동의한 회원에게는 이메일로 공지해 드리고 있습니다.

1. 개인정보 수집에 대한 동의

회원 가입 시 [개인정보취급방침 및 이용약관에 동의함] 버튼을 클릭하면 개인정보 수집에 동의한 것으로 간주하여 개인정보를 DB(데이터베이스)에 저장하고 명시된 개인정보의 이용목적에 이용됩니다.

2. 개인정보 수집 범위

회원에 가입할 경우 다음과 같은 개인정보 항목을 수집하고 있습니다. 필수항목은 반드시 입력해야 하는 항목이며, 선택항목은 입력하지 않더라도 당사의 서비스를 이용하는데 제한이 없습니다.

 
필수항목

선택항목

홈페이지

회원 가입 시

이름, 아이디, 비밀번호, 핸드폰번호, 이메일주소, 

진료

예약 시

이름,  핸드폰번호, 이메일주소, 


* 이외에 회원의 편리 및 서비스 확인을 위해 서비스를 이용한 경우에는 최근방문병원 및 예약내역 정보가 자동으로 생성됩니다.

3. 개인정보의 수집•이용목적

수집된 개인정보는 다음과 같은 목적으로 이용됩니다.

개인정보 항목

수집•이용목적

아이디, 비밀번호, 핸드폰번호, 성명, 

서비스 이용을 위한 본인 식별

이메일주소

비밀번호 변경(재발급)

(수신 동의한 경우 : 이벤트 안내, 서비스 이용관련 정보, 공지사항 등 최신정보 안내)

성명, 주소, 전화번호, 핸드폰번호, 아이디

본인 확인 및 연락 시, 공지 및 물품 배송지로 이용


성명,   전화번호, 핸드폰번호, 이메일주소, 예약정보

진료 예약서비스 이용 시 병원에 정보 제공
진료 예약시 건강보험 가입여부, 건강검진 대상여부 등 일정 사항 확인

최근 방문 병원 정보(회원이 서비스 이용 시의 정보를 기준으로 자동 추출됨), 진료예약 내역

편리하게 서비스를 재이용할 수 있는 용도, 서비스 이용 확인 용도

4. 개인정보를 제3자에 대한 제공

당사는 제5조의 취급위탁을 제외하고 제3자에게 개인정보를 제공하지 않습니다.


5. 개인정보를 제3자에 취급 위탁

당사는 귀하의 동의가 있거나 법률 규정에 의한 경우를 제외하고는 [개인정보의 수집•이용목적]에 명시한 범위를 벗어나서 개인정보를 이용하거나 제공하지 않지만 다음과 같은 경우에는 개인 정보를 이용 및 제공할 수 있습니다.

상세 내용

의료기관에 취급위탁

- 취급 업체: 당사의 서비스를 이용하는 의료기관.

회원이 해당 의료기관에 초진으로 방문하거나 당사 서비스를

이용하여 방문한 경우에 제공 (예약가능 병원)

- 위탁 목적: 회원이 의료기관 방문 시 제공해야 하는
정보를 편의상 제공, 서비스 내용

- 위탁 범위: 성명,  전화번호, 핸드폰번호, 이메일주소, 예약정보

- 의료기관에서의 개인정보 보유 및 이용기간: 이후 의료기관에서 개인정보 관리(통상 의료기관에 방문하여 개인정보를 제공하였을 경우와 상동)


매각, 인수합병 등

개인정보 승계 및 이전되는 경우에는 사전에 공지 및 동의방법을 통하여 적극적으로 알리며 동의 철회에 대한 선택권을 부여합니다.

예외

다음과 같은 경우에는 예외적으로 회원의 동의 없이 개인정보를 제공할 수 있습니다. 최대한 사전에 회원님께 고지하는 것을 원칙으로 하나 법률상에 의해 부득이하게 고지를 하지 못할 수도 있습니다.

1. 관계법령에 의하여 수사상의 목적으로 관계기관으로부터 요구 받은 경우

2. 정보통신윤리위원회의 요청이 있는 경우

3. 기타 관계법령에 의한 경우

4. 특정 개인을 식별할 수 없는 통계작성, 홍보자료, 학술연구 등의 목적일 때


6. 개인정보의 보유 및 이용기간

개인정보는 회원탈퇴 후 파기됩니다. 회원탈퇴 후의 보유 항목 및 보유기간은 다음과 같습니다

상세 내용

회원 탈퇴 시 보관하지 않음을 선택한 경우

보유 기간: 30일

회원 탈퇴 시 “보관함”을 선택한 경우

보유 항목: 수집범위에 명시된 개인정보 항목

보유 기간: 30일

비활동 회원인 경우

3년간 로그인 하지 않은 회원은 비활동 회원으로 분류되어 이메일로 공지 후 7일 이후에도 로그인하지 않은 때에는 그 정보를 언제든지 삭제할 수 있습니다.

7. 쿠키에 의한 개인정보 수집 및 거부

8. 개인정보의 열람 및 정정

언제든지 홈페이지에서 로그인한 후 개인정보수정 항목을 클릭하여 개인정보를 열람 및 정정할 수 있습니다. 로그인하여 열람할 수 없는 경우에는 전화 또는 이메일로 연락 주시면 본인확인 후 조치하여 드립니다.


9. 개인정보 관리의 기술적, 관리적 대책 방안

10. 개인정보 수집, 이용 및 제공에 대한 동의 철회방법

개인정보 수집, 이용 및 제공에 대한 동의를 철회하시려면 당사 홈페이지에서 로그인하신 후 My Room->회원탈퇴를 클릭하시어 회원탈퇴 하시면 5. 개인정보 보유 및 이용기간에 명시된 보유기간 이후에 개인정보는 파기됩니다.

 

11. 14세미만 어린이의 개인정보관리 방안

14세미만의 어린이는 회원의 가족으로만 등록 가능합니다. 회원님이 가족을 등록하시면 그 가족의 법정대리인으로 간주합니다. 회원님은 언제든지 가족회원의 정보를 열람 및 정정할 수 있습니다.

12. 회원탈퇴

①회원이 회원가입 계약을 해지하고자 하는 경우에는 서비스내 회원탈퇴 기능을(로그인 후 MyRoom -> 회원탈퇴) 이용하여 탈퇴의사를 밝혀야 하며, 탈퇴신청에 대해 '당사'가 빠른 시간 내로 확인하여 탈퇴처리를 할 의무가 있습니다.
②회원이 3년 이상 로그인을 하지 않았을 경우에는 회원의 동의 없이 ‘당사’가 회원탈퇴 처리를 할 수 있습니다. 단, 회원이 회원탈퇴를 원하는 경우에는 반드시 회원탈퇴 요청을 하여 확실히 처리되도록 하는 것이 회원의 의무입니다.
③'당사'가 회원이 제17조 회원의 의무에 위배되는 행위를 한 경우 사전통지 없이 회원가입계약을 해지하거나 회원자격을 적절한 방법으로 제한 및 정지할 수 있습니다.

 
13. 개인정보 관련 민원서비스

회원의 개인정보를 보호하고 개인정보와 관련한 불만을 해소하고자 다음과 같이 개인정보관리 책임자를 지정하고 있습니다. 개인정보보호관련 민원은 개인정보관리 책임자에게 신고하실 수 있으며 신고사항에 대해 신속히 처리하도록 최선을 다하겠습니다.


<개인정보관리 책임자>

성    명: 장성민

전화번호: 010-1111-2222

이 메 일: (jsm@medicovid.com)

업무시간: 월~금 9:00 ~ 18:00


[부칙]

본 약관은 2011. 8. 02. 부터 적용합니다.
   </textarea>
					</div>
					<div class="checkbox">
						<p>
							MediCovid 약관에 동의합니다.
							<input type="checkbox" name="chek" id="chek" value="1" />
						</p>
					</div>
				</div>
			</div>

			<div class="button-div">
				<input type="button" id="btn1" value="회원가입" />
			</div>
		</form>
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