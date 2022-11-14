//모달부분
function setMember() {
	document.querySelector('.rev_modal').style.display = 'block';
	document.querySelector('.back_modal2').style.display = 'block';
}

function setMember2(index) {
	console.log(index);
	document.getElementById(index).style.display = 'block';
	document.querySelector('.back_modal2').style.display = 'block';

}

function modalclose() {
	document.querySelector('.rev_modal').style.display = 'none';
	document.querySelector('.back_modal2').style.display = 'none';

}

function modalclose2(index) {
	document.getElementById(index).style.display = 'none';
	document.querySelector('.back_modal2').style.display = 'none';

}

$(function() {


	//모든 공백 체크 정규식
	var empJ = /\s/g;

	// 비밀번호 정규식
	var pwJ = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

	// 휴대폰 번호 정규식
	var phoneJ = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;

	// 마이페이지 => 내 정보 수정 모달창 => 비밀번호 형식 정규식
	$('#newPw').on("keyup", function() {
		if (pwJ.test($('#newPw').val())) {
			console.log('true');
			$('#checkPw').text('사용할 수 있는 비밀번호입니다.');
			$('#checkPw').css('color', 'green');
		} else {
			console.log('false');
			$('#checkPw').text('형식에 맞는 비밀번호를 입력하세요');
			$('#checkPw').css('color', 'red');
		}
	});

	// 마이페이지 => 내 정보 수정 모달창 => 비밀번호 일치확인 정규식
	$('#newPw2').on("keyup", function() {
		if ($('#newPw').val() != $(this).val()) {
			$('#checknewPw').text('비밀번호가 일치하지 않습니다.');
			$('#checknewPw').css('color', 'red');
		} else {
			$('#checknewPw').text('비밀번호가 일치합니다.');
			$('#checknewPw').css('color', 'green');
		}
	});

	// 휴대전화
	$('#newPhone').on("keyup", function() {
		if (phoneJ.test($(this).val())) {
			// console.log(nameJ.test($(this).val()));
			$('#checknewPhone').text('올바른 휴대폰 번호입니다.');
			$('#checknewPhone').css('color', 'green');
		} else {
			$('#checknewPhone').text('휴대폰번호를 확인해주세요. ');
			$('#checknewPhone').css('color', 'red');
		}
	});

	$(".ing_a").on("click", function() {
		$(".ing_a").removeClass("clicked");
		$(this).addClass("clicked");
	});
})
/*
const nonClick = document.querySelectorAll(".ing_a");

function handleClick(event){
	nonClick.forEach((e) => {
		e.classList.remove("click");
	});
	event.target.classList.add("click");
}

nonClick.forEach((e) => {
	e.addEventListener("click", handleClick);
});
*/



/*function init() {
	for (var i = 0; i < ing_a.length; i++) {
		ing_a[i].addEventListener("click", handleClick);
	}
}*/


/*
var ing_a = document.getElementsByClassName('ing_a');

for (var i = 0; i < ing_a.length; i++) {
	ing_a[i].addEventListener('click', function() {
		for (var j = 0; j < ing_a.length; j++) {
			ing_a[j].style.color = "black";
		}
		this.style.color = "gold";
	})
}
*/

/*
$("#IngReservation").on("click", function () {
  $("#IngReservation").css('color', 'inherit');
  $(this).css('color', '#1088a6');
});
*/

// 마이페이지(지난 예약 내역) => 더 보기
function passedseeplus() {
	console.log("test: ");
	console.log(document.getElementById('uno').value);
	var uno = document.getElementById('uno').value;

	$.ajax({
		type: 'POST',
		url: '/ajaxmoreseepassed',
		data: { uno: uno },
		success: function(data) {
			console.log("성공");
			//console.log(data);
			$('#ajaxlist').html(data);
		},
		error: function() {
			console.log("실패..");
		},
		complete: function() {
			console.log("완료");
		},
		beforeSend: function() {
			console.log("이제 보낼거다..")
		}
	});
}

// 마이페이지(진행중인 예약) => 더 보기
function ingseeplus() {
	console.log("test: ");
	console.log(document.getElementById('uno').value);
	var uno = document.getElementById('uno').value;

	$.ajax({
		type: 'POST',
		url: '/ajaxmoresee',
		data: { uno: uno },
		success: function(data) {
			console.log("성공");
			//console.log(data);
			$('#ajaxlist').html(data);
		},
		error: function() {
			console.log("실패..");
		},
		complete: function() {
			console.log("완료");
		},
		beforeSend: function() {
			console.log("이제 보낼거다..")
		}
	});
}



// 마이페이지 => 예약 취소 버튼
function reservationcancel(hname, rdate, rtime, rno) {
	var result = confirm(hname + " " + rdate + " " + rtime + " 예약을 취소하시겠습니까?");
	console.log(rno);
	if (result == true) {

		$.ajax({
			type: 'GET',
			url: '/ajaxreservationcancle',
			data: { rno: rno },
			dataType: 'text',
			success: function(data) {
				console.log("성공");
				console.log(data);

			},
			error: function() {
				console.log("실패..");
			},
			complete: function() {
				console.log("완료");
				location.href = "mypage";
			},
			beforeSend: function() {
				console.log("이제 보낼거다..");
			}
		});


		alert("예약이 취소 되었습니다.");
	}
}
// 내 정보 수정 모달창 => 회원탈퇴 버튼
function dropuser(uno) {
	if (confirm("MediCovid 회원 탈퇴를 진행하시겠습니까?")) {
		$("#frm").attr("action", "userdrop");
		//console.log($("#frm").attr("action"));

		$("#frm").submit();
	}
	console.log("uno : " + uno);



	alert("회원탈퇴가 완료되었습니다.");

	/*
	if ($("#existedPw").val() == "") {
		alert("기존 비밀번호를 입력해주세요.")
	} else if (result == true) {
		alert("회원탈퇴가 완료되었습니다.");
	}
	*/
}


// 내 정보 수정 모달창 => 수정완료 버튼
function modifyuserinfo() {
	var result = confirm("회원정보를 수정하시겠습니까?");

	if ($("#existedPw").val() == "") {
		alert("기존 비밀번호를 입력해주세요.")
		$("#existedPw").focus();
		return;
	} else if ($("#newPw").val() == "") {
		alert("변경할 비밀번호를 입력해주세요.")
		$("#newPw").focus();
		return;
	} else if ($("#newPw2").val() == "") {
		alert("변경할 비밀번호를 한번 더 입력해주세요.")
		$("#newPw2").focus();
		return;
	} else if ($("#newPw").val() != $("#newPw2").val()) {
		alert("변경할 비밀번호가 일치하지 않습니다.")
		$("#newPw2").val("");
		$("#newPw2").focus();
		return;
	} else if ($("#existedPw").val() == $("#newPw2").val()) {
		alert("기존 비밀번호와 다른 비밀번호를 입력해주세요.")
		$("#existedPw").val("");
		$("#newPw2").val("");
		$("#existedPw").focus();
		return;
	} else if ($("#existedPw").val().length < 8 || $("#existedPw").val().length > 20) {
		alert("8~20 범위의 비밀번호를 입력해주세요.");
		$("#existedPw").val("");
		$("#newPw2").val("");
		$("#existedPw").focus();
		return;
	} else if ($("#user_pw").val() != $("#existedPw").val()) {
		alert("기존 비밀번호가 올바르지 않습니다.")
		$("#pw").val("");
		$("#pw").focus();
		return;
	}

	if (result == false) {

	} else {
		alert("회원정보가 수정되었습니다.");
	}

}

// 이용후기 모달창 => 후기등록 버튼
function reviewsubmit() {
	alert("이용후기가 등록되었습니다.");
}


// 지도 표시 및 마커 표시
var hx = [];
var hy = [];

$(".hx").each(function(index, item) {
	hx.push($(item).val());
});

$(".hy").each(function(index, item) {
	hy.push($(item).val());
});

var count = document.getElementsByClassName('mapapi').length;

for (var i = 0; i < count; i++) {
	var mapContainer = document.getElementById('mapapi' + i), // 지도를 표시할 div 
		mapOption = {
			center: new kakao.maps.LatLng(hy[i], hx[i]), // 지도의 중심좌표
			level: 3, // 지도의 확대 레벨
			mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
		};


	// 지도를 생성한다 
	var map = new kakao.maps.Map(mapContainer, mapOption);

	var markerPosition = new kakao.maps.LatLng(hy[i], hx[i]);

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
}

