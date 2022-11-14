/**
 * 
 */

$(function() {
	hospitalPaging();
	userPaging();

	// 사용자가 입력한 운영시작시간, 종료시간을 하나로 합쳐서 htime 에 넣기
	$("#time1,#time2").on("keyup", function() {
		$("#htime").val($("#time1").val() + $("#time2").val());
	});

	// 주소값을 하나로 합쳐서 haddress에 넣기 + 좌표값 넣기
	$("#detailAddress").on("keyup", function() {
		var address = $("#address").val() + " " + $("#detailAddress").val();
		$("#haddress").val(address);
		getCord($("#address").val());
	});


	// 병원 등록 폼 생성
	$("#hospitalRegistration").on("click", function() {
		$("#hospitalRegistrationForm").show();
		$("#userPenaltyForm").hide();
		$("#hospitalRegistrationForm .modal-title").text("병원 정보 등록");
		$("#hospitalFrm").attr("action", "./hospitals");

		$("input[type='checkbox']").prop("checked", false);
		$("#hno").val("1");
		$("#hname").val("");
		$("#htime").val("");
		$("#time1").val("");
		$("#time2").val("");
		$("#postcode").val("");
		$("#address").val("");
		$("#detailAddress").val("");
		$("#extraAddress").val("");
		$("#haddress").val("");
		$("#hgu").val("");
		$("#hx").val("");
		$("#hy").val("");
		$("#htel").val("");

		$("input[value='등록']").attr("type", "button");
		$("input[value='수정']").attr("type", "hidden");
		$("input[value='삭제']").attr("type", "hidden");
	});

	// 병원 정보 수정,삭제 폼 생성
	$(".tableDiv1 tbody").on("click", ".hospitalModification", function() {
		$("#hospitalRegistrationForm").show();
		$("#userPenaltyForm").hide();
		$("#hospitalRegistrationForm .modal-title").text("병원 정보 관리");

		const hno = $(this).children("td")[0].innerText;
		$("#hospitalFrm").attr("action", "./hospitals/" + hno);

		$("#hno").val(hno);
		$("#hname").val($(this).children("td")[1].innerText);
		$("#htel").val($(this).children("td")[3].innerText);
		$("#htime").val($(this).children("td")[4].children[0].innerText);

		let time = $(this).children("td")[4].children[0].innerText;
		$("#time1").val(time.substr(0, 4));
		$("#time2").val(time.substr(4, 4));

		$("input[type='checkbox']").prop("checked", false);
		$("#postcode").val("");
		$("#address").val("");
		$("#detailAddress").val("");
		$("#extraAddress").val("");
		$("#haddress").val("");
		$("#hgu").val("");
		$("#hx").val("");
		$("#hy").val("");

		$("input[value='등록']").attr("type", "hidden");
		$("input[value='수정']").attr("type", "button");
		$("input[value='삭제']").attr("type", "button");
	});

	// 병원 등록,수정,삭제 모달창 닫기
	$("#closeHospitalRegistrationForm").on("click", function() {
		$("#hospitalRegistrationForm").hide();
	});

	// 병원 정보 등록
	$("input[value='등록']").on("click", function() {
		var cnt = $("#hospitalFrm input[type='checkbox']:checked").length;
		var telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
		var timeRegex = /^\d{4}$/;

		if (cnt == 0) {
			alert("구분을 1개 이상 선택하여야 합니다.");
			$("#hospitalFrm input[type='checkbox']").focus();
			return;
		} else if ($("#hname").val() == null || $("#hname").val() == "") {
			alert("병원명을 입력해주세요.");
			$("#hname").focus();
			return;
		} else if ($("#time1").val() == null || $("#time1").val() == "") {
			alert("운영 시작 시간을 입력해주세요.");
			$("#time1").focus();
			return;
		} else if (!timeRegex.test($("#time1").val())) {
			alert("운영 시작 시간을 형식에 맞추어 입력해주세요.");
			$("#time1").focus();
			$("#time1").val("");
			return;
		} else if (!($("#time1").val().substring(2, 4) == 30 || $("#time1").val().substring(2, 4) == 00)) {
			alert("운영 시작 시간을 형식에 맞추어 입력해주세요. 4자리 숫자에 뒷 두자리는 00 또는 30으로 끝나야 합니다.");
			$("#time1").focus();
			$("#time1").val("");
			return;
		} else if ($("#time2").val() == null || $("#time2").val() == "") {
			alert("운영 종료 시간을 입력해주세요.");
			$("#time2").focus();
			return;
		} else if (!timeRegex.test($("#time2").val())) {
			alert("운영 종료 시간을 형식에 맞추어 입력해주세요.");
			$("#time2").focus();
			$("#time2").val("");
			return;
		} else if (!($("#time2").val().substring(2, 4) == 30 || $("#time2").val().substring(2, 4) == 00)) {
			alert("운영 종료 시간을 형식에 맞추어 입력해주세요. 4자리 숫자에 뒷 두자리는 00 또는 30으로 끝나야 합니다.");
			$("#time2").focus();
			$("#time2").val("");
			return;
		} else if ($("#address").val() == null || $("#address").val() == "") {
			alert("주소를 입력해주세요.");
			$("#postcode").focus();
			return;
		} else if ($("#detailAddress").val() == null || $("#detailAddress").val() == "") {
			alert("상세 주소를 입력해주세요.");
			$("#detailAddress").focus();
			return;
		} else if ($("#htel").val() == null || $("#htel").val() == "") {
			alert("전화번호를 입력해주세요.");
			$("#htel").focus();
			return;
		} else if (!telRegex.test($("#htel").val())) {
			alert("전화번호를 형식에 맞추어 입력해주세요.");
			$("#htel").focus();
			$("#htel").val("");
			return;
		}

		$("input[name='_method']").attr("value", "post");
		$("#hospitalFrm").submit();
	});

	// 병원 정보 수정
	$("input[value='수정']").on("click", function() {
		var cnt = $("#hospitalFrm input[type='checkbox']:checked").length;
		var telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
		var timeRegex = /^\d{4}$/;

		if (cnt == 0) {
			alert("구분을 1개 이상 선택하여야 합니다.");
			$("#hospitalFrm input[type='checkbox']").focus();
			return;
		} else if ($("#hname").val() == null || $("#hname").val() == "") {
			alert("병원명을 입력해주세요.");
			$("#hname").focus();
			return;
		} else if ($("#time1").val() == null || $("#time1").val() == "") {
			alert("운영 시작 시간을 입력해주세요.");
			$("#time1").focus();
			return;
		} else if (!timeRegex.test($("#time1").val())) {
			alert("운영 시작 시간을 형식에 맞추어 입력해주세요.");
			$("#time1").focus();
			$("#time1").val("");
			return;
		} else if (!($("#time1").val().substring(2, 4) == 30 || $("#time1").val().substring(2, 4) == 00)) {
			alert("운영 시작 시간을 형식에 맞추어 입력해주세요. 4자리 숫자에 뒷 두자리는 00 또는 30으로 끝나야 합니다.");
			$("#time1").focus();
			$("#time1").val("");
			return;
		} else if ($("#time2").val() == null || $("#time2").val() == "") {
			alert("운영 종료 시간을 입력해주세요.");
			$("#time2").focus();
			return;
		} else if (!timeRegex.test($("#time2").val())) {
			alert("운영 종료 시간을 형식에 맞추어 입력해주세요.");
			$("#time2").focus();
			$("#time2").val("");
			return;
		} else if (!($("#time2").val().substring(2, 4) == 30 || $("#time2").val().substring(2, 4) == 00)) {
			alert("운영 종료 시간을 형식에 맞추어 입력해주세요. 4자리 숫자에 뒷 두자리는 00 또는 30으로 끝나야 합니다.");
			$("#time2").focus();
			$("#time2").val("");
			return;
		} else if ($("#address").val() == null || $("#address").val() == "") {
			alert("주소를 입력해주세요.");
			$("#address").focus();
			return;
		} else if ($("#detailAddress").val() == null || $("#detailAddress").val() == "") {
			alert("상세 주소를 입력해주세요.");
			$("#detailAddress").focus();
			return;
		} else if ($("#htel").val() == null || $("#htel").val() == "") {
			alert("전화번호를 입력해주세요.");
			$("#htel").focus();
			return;
		} else if (!telRegex.test($("#htel").val())) {
			alert("전화번호를 형식에 맞추어 입력해주세요.");
			$("#htel").focus();
			$("#htel").val("");
			return;
		}

		$("input[name='_method']").attr("value", "put");
		$("#hospitalFrm").submit();
	});

	// 병원 정보 삭제
	$("input[value='삭제']").on("click", function() {
		if (confirm("정말 삭제하시겠습니까?")) {
			$("input[name='_method']").attr("value", "delete");
			$("#hospitalFrm").submit();
		}
	});

	// 사용자 패널티 등록,제거 폼 생성
	$(".tableDiv2 tbody ").on("click", ".userPenaltyModification", function() {
		$("#userPenaltyForm").show();
		$("#hospitalRegistrationForm").hide();

		const uno = $(this).children("td")[0].innerText;
		$("#userFrm").attr("action", "./users/" + uno);

		$("#userFrm > div.modal-body > p:nth-child(2) > span").text($(this).children("td")[1].innerText);
		$("#userFrm > div.modal-body > p:nth-child(3) > span").text($(this).children("td")[2].innerText);
	});

	// 사용자 패널티 모달창 닫기
	$("#closeUserPenaltyForm").on("click", function() {
		$("#userPenaltyForm").hide();
	});

	// 사용자에 패널티 부여
	$("input[value='패널티 적용']").on("click", function() {
		var penalty = $("input[name='penalty']");
		var numOnly = /^[0-9]*$/;

		if (penalty.val() == null || penalty.val() == "") {
			alert("패널티를 입력해주세요.");
			penalty.focus();
			return;
		} else if (!numOnly.test(penalty.val())) {
			alert("숫자만 입력 가능합니다.");
			penalty.focus();
			penalty.val("");
			return;
		} else if (penalty.val().length > 5) {
			alert("최대 패널티는 9999일 입니다.");
			penalty.focus();
			penalty.val("");
			return;
		}

		$("#userFrm input[name='_method']").attr("value", "put");
		$("#userFrm").submit();
	});

	// 사용자 삭제
	$("input[value='회원 삭제']").on("click", function() {
		if (confirm("이 사용자를 정말 삭제하시겠습니까?")) {
			$("#userFrm input[name='_method']").attr("value", "delete");
			$("#userFrm").submit();
		}
	});

	// 병원 검색 버튼 클릭 시 searchBy 함수 실행
	$(".searchDiv1 .searchBtn").on("click", searchBy);
	// 병원 검색 인풋에서 엔터 시 searchBy 함수 실행
	$(".searchDiv1 #hospitalKeyword").on("keyup", function(e) {
		if (e.keyCode == 13) {
			searchBy();
		}
	});

	// 사용자 검색 버튼 클릭 시 searchByUid 함수 실행
	$(".searchDiv2 .searchBtn").on("click", searchByUid);
	// 사용자 검색 인풋에서 엔터 시 searchByUid 함수 실행
	$(".searchDiv2 #userKeyword").on("keyup", function(e) {
		if (e.keyCode == 13) {
			searchByUid();
		}
	});

	// 병원 관리 페이지 숫자 버튼 이벤트
	$(".paginationDiv1 ul").on("click", ".pageButton", function() {
		$(".pageButton").removeClass("currentPageH");
		$(this).addClass("currentPageH");
		hospitalPaging($(this).text());
	});

	// 병원 관리 페이지 < 버튼 이벤트
	$(".paginationDiv1 ul").on("click", ".prev", function() {
		var hcp = parseInt($(".currentPageH").text());

		if (hcp <= 10) {
			hospitalPaging(1);
		} else {
			hospitalPaging(hcp - 10);
		}
	});

	// 병원 관리 페이지 > 버튼 이벤트
	$(".paginationDiv1 ul").on("click", ".next", function() {
		var hcp = parseInt($(".currentPageH").text());
		// console.log(parseInt($(".currentPageH").text())+5);
		hospitalPaging(hcp + 10);
	});

	// 사용자 관리 페이지 숫자 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".pageButtonU", function() {
		$(".pageButton").removeClass("currentPageU");
		$(this).addClass("currentPageU");
		userPaging($(this).text());
	});

	// 사용자 관리 페이지 < 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".prev", function() {
		var ucp = parseInt($(".currentPageU").text());
		if (ucp <= 10) {
			userPaging(1);
		} else {
			userPaging(ucp - 10);
		}
	});

	// 사용자 관리 페이지 > 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".next", function() {
		var ucp = parseInt($(".currentPageU").text());
		userPaging(ucp + 10);
	});

	// 사용자 관리 페이지 검색 후 숫자 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".pageButtonUS", function() {
		$(".pageButton").removeClass("currentPageUS");
		$(this).addClass("currentPageUS");
		searchByUid($(this).text());
	});

	// 사용자 관리 페이지 검색 후 < 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".prevUS", function() {
		var ucp = parseInt($(".currentPageUS").text());
		if (ucp <= 10) {
			searchByUid(1);
		} else {
			searchByUid(ucp - 10);
		}
	});

	// 사용자 관리 페이지 검색 후 > 버튼 이벤트
	$(".paginationDiv2 ul").on("click", ".nextUS", function() {
		var ucp = parseInt($(".currentPageUS").text());
		searchByUid(ucp + 10);
	});


});

function userPaging(cp) {
	$.ajax({
		method: "GET",
		url: "/admin/users",
		data: {
			cp: cp
		},
		dataType: "json",
		success: function(result) {
			//console.log(result);
			var list = new Array;
			list = result.userList;
			//console.log(list);
			var paging = result.userPaging;
			var currentPage = paging.currentPage;
			var startPage = paging.startPage;
			var endPage = paging.endPage;
			var prev = paging.prev;
			var next = paging.next;
			//console.log(paging + ", " + currentPage + ", " + startPage + ", " + endPage + ", " + prev + ", " + next);

			var tableHtml = "";
			var pagingHtml = "";

			$(".usersTable>tbody").empty();

			$.each(list, function(key, value) {
				tableHtml = "<tr class='userPenaltyModification'>"
				tableHtml += "<td>" + value.uno + "</td>"
				tableHtml += "<td>" + value.uname + "</td>"
				tableHtml += "<td>" + value.uid + "</td>"
				tableHtml += "<td>" + value.utel + "</td>"
				tableHtml += "<td>" + value.penalty + "일 정지 </td>"
				tableHtml += "</tr>"
				//console.log("str : " + str);
				$(".usersTable>tbody").append(tableHtml);
			});

			$(".paginationDiv2 ul").empty();

			if (prev) {
				pagingHtml += "<li class='arrow prev'><img src='../images/left.png' alt='10페이지 전으로'/></li>";
			}

			for (var i = startPage; i <= endPage; i++) {

				if (i == currentPage) {
					pagingHtml += "<li class='pageButton pageButtonU currentPageU'>" + i + "</li>"
				} else {
					pagingHtml += "<li class='pageButton pageButtonU'>" + i + "</li>"
				}
			}

			if (next) {
				pagingHtml += "<li class='arrow next'><img src='../images/right.png' alt='10페이지 뒤로'/></li>";
			}

			$(".paginationDiv2 ul").append(pagingHtml);
		}
	});
}

function hospitalPaging(cp) {
	//console.log("cp : " + cp);

	$.ajax({
		method: "GET",
		url: "/admin/hospitals",
		data: {
			cp: cp
		},
		dataType: "json",
		success: function(result) {
			//console.log(result);
			var list = new Array;
			list = result.hospitalList;
			// console.log(list);
			var paging = result.hospitalPaging;
			var currentPage = paging.currentPage;
			var startPage = paging.startPage;
			var endPage = paging.endPage;
			var prev = paging.prev;
			var next = paging.next;
			//console.log(paging + ", " + currentPage + ", " + startPage + ", " + endPage + ", " + prev + ", " + next);

			var tableHtml = "";
			var pagingHtml = "";

			$(".hospitalsTable>tbody").empty();

			$.each(list, function(key, value) {
				tableHtml = "<tr class='hospitalModification'>"
				tableHtml += "<td>" + value.hno + "</td>"
				tableHtml += "<td>" + value.hname + "</td>"
				tableHtml += "<td>" + value.haddress + "</td>"
				tableHtml += "<td>" + value.htel + "</td>"
				tableHtml += "<td><span>" + value.htime + "</span>" + value.htime.substring(0, 2) + ":" + value.htime.substring(2, 4) + " ~ " + value.htime.substring(4, 6) + ":" + value.htime.substring(6, 8) + "</td>"
				tableHtml += "</tr>"
				//console.log("str : " + str);
				$(".hospitalsTable>tbody").append(tableHtml);
			});

			$(".paginationDiv1 ul").empty();

			if (prev) {
				pagingHtml += "<li class='arrow prev'><img src='../images/left.png' alt='10페이지 전으로'/></li>";
			}

			for (var i = startPage; i <= endPage; i++) {

				if (i == currentPage) {
					pagingHtml += "<li class='pageButton currentPageH'>" + i + "</li>"
				} else {
					pagingHtml += "<li class='pageButton'>" + i + "</li>"
				}
			}

			if (next) {
				pagingHtml += "<li class='arrow next'><img src='../images/right.png' alt='10페이지 뒤로'/></li>";
			}

			$(".paginationDiv1 ul").append(pagingHtml);
		}
	});
}

function searchByUid() {
	var uid = $("#userKeyword").val();
	//console.log(uid);

	if (uid == null || uid == "") {
		userPaging();
	} else {
		$.ajax({
			method: "GET",
			url: "/admin/users/" + uid,
			dataType: "json",
			success: function(result) {
				//console.log(result);
				var list = new Array;
				list = result.userList;
				//console.log(list);
				var paging = result.userPaging;
				var currentPage = paging.currentPage;
				var startPage = paging.startPage;
				var endPage = paging.endPage;
				var prev = paging.prev;
				var next = paging.next;
				//console.log(paging + ", " + currentPage + ", " + startPage + ", " + endPage + ", " + prev + ", " + next);

				var tableHtml = "";
				var pagingHtml = "";

				$(".usersTable>tbody").empty();

				if (list.length == 0) {
					alert("문자열 '" + uid + "' 이/가 들어가는 사용자가 아이디가 없습니다.");
				}
				$.each(list, function(key, value) {
					tableHtml = "<tr class='userPenaltyModification'>"
					tableHtml += "<td>" + value.uno + "</td>"
					tableHtml += "<td>" + value.uname + "</td>"
					tableHtml += "<td>" + value.uid + "</td>"
					tableHtml += "<td>" + value.utel + "</td>"
					tableHtml += "<td>" + value.penalty + "일 정지 </td>"
					tableHtml += "</tr>"
					//console.log("str : " + str);
					$(".usersTable>tbody").append(tableHtml);
				});

				$(".paginationDiv2 ul").empty();

				if (prev) {
					pagingHtml += "<li class='arrow prevUS'><img src='../images/left.png' alt='10페이지 전으로'/></li>";
				}

				for (var i = startPage; i <= endPage; i++) {

					if (i == currentPage) {
						pagingHtml += "<li class='pageButton pageButtonUS currentPageUS'>" + i + "</li>"
					} else {
						pagingHtml += "<li class='pageButton pageButtonUS'>" + i + "</li>"
					}
				}

				if (next) {
					pagingHtml += "<li class='arrow nextUS'><img src='../images/right.png' alt='10페이지 뒤로'/></li>";
				}

				$(".paginationDiv2 ul").append(pagingHtml);

			},
			error: function(data, textStatus) {
				alert(data + ", " + textStatus);
			}
		});
	}

	$("#userKeyword").focus();
}

function searchBy() {
	const hno = $("#hospitalKeyword").val();

	if (hno == null || hno == "") {
		// 검색어 안 넣고 검색을 누른 경우 전체 목록 불러오기
		hospitalPaging();
	} else {
		// 검색어가 있다면 그에 맞는 목록만 불러오기
		$.ajax({
			method: "GET",
			url: "/admin/hospitals/" + hno,
			dataType: "json",
			success: function(result) {
				var data = result;
				var str = "";
				//console.log(result);
				$(".hospitalsTable>tbody").empty();
				$(".paginationDiv1 ul").empty();

				$.each(data, function() {
					str = "<tr class='hospitalModification'>"
					str += "<td>" + data.hno + "</td>"
					str += "<td>" + data.hname + "</td>"
					str += "<td>" + data.haddress + "</td>"
					str += "<td>" + data.htel + "</td>"
					str += "<td><span>" + data.htime + "</span>" + data.htime.substring(0, 2) + ":" + data.htime.substring(2, 4) + " ~ " + data.htime.substring(4, 6) + ":" + data.htime.substring(6, 8) + "</td>"
					str += "</tr>"
					//console.log("str : " + str);
				});
				$(".hospitalsTable>tbody").append(str);
			},
			error: function(data, textStatus) {
				console.log(data);
				console.log(textStatus);
				alert("존재하지 않는 병원번호(아이디) 입니다.");
			}
		});
	}

	$("#hospitalKeyword").focus();
	$("#hospitalKeyword").val("");

}

function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.			

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("extraAddress").value = extraAddr;

			} else {
				document.getElementById("extraAddress").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById("address").value = addr;
			// 구만 따로 추출해서 컬럼에 넣어야 하므로 addr 값에서 구만 추출하여 #hgu의 value로 넣음
			document.getElementById("hgu").value = addr.split(" ").splice(1, 1);

			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("detailAddress").focus();
		}
	}).open();
}


function getCord(address) {
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(address, function(result, status) {

		// 정상적으로 검색이 완료됐으면 
		if (status === kakao.maps.services.Status.OK) {
			$("#hx").val(result[0].x);
			$("#hy").val(result[0].y);
		}
	});
}

