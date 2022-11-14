window.onload = function(){
	
	//로드시 운영시간 placeholder 지정
	var runtime = document.getElementById('runtime').value;	
	var starttime = document.querySelector('#time1');
	var endtime = document.querySelector('#time2');
	starttime.value = runtime.substring(0, 4);
	endtime.value = runtime.substring(4,8);

	//로드시 카테고리 체크 지정
	var cat1 = document.getElementById('cat1').value;
	var cat2 = document.getElementById('cat2').value;
	var cat3 = document.getElementById('cat3').value;
	if(cat1 == "true"){
		document.getElementById("category1").checked=true;
	}
	if(cat2 == "true"){
		document.getElementById("category2").checked=true;
	}
	if(cat3 == "true"){
		document.getElementById("category3").checked=true;
	}

	
	//주소 기본값 지정
	var adr = document.getElementById('basicaddress').value;
	document.getElementById('address').value = adr.split('(')[0];
	document.getElementById('detailAddress').value= adr.split('(')[1].split(')')[0];


}	
	


//비밀번호 일치 확인
function check_newpw(){
	var in1 = document.getElementById('newpw').value;
	var in2 = document.getElementById('newpwcheck').value;
	if(in1 == in2){
		document.getElementById('pwcheck').innerText ="일치";
	}else{
		document.getElementById('pwcheck').innerText ="일치하지 않습니다";		
	}
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
	
	
	

	
	
	
	
$(function(){
	
	
	// 주소값을 하나로 합쳐서 haddress에 넣기 + 좌표값 넣기
	$("#detailAddress").on("keyup", function() {
		var address = $("#address").val() + " (" + $("#detailAddress").val()+")";
		$("#haddress").val(address);
		getCord($("#address").val());
	});
	

	// 사용자가 입력한 운영시작시간, 종료시간을 하나로 합쳐서 htime 에 넣기
	$("#time1,#time2").on("keyup", function() {
		$("#htime").val($("#time1").val() + $("#time2").val());
	});


	// 병원 정보 수정
	$("input[value='수정']").on("click", function() {
		
		var cnt = $("#hospitalFrm input[type='checkbox']:checked").length;
				
		var telRegex = /^\d{2,3}-\d{3,4}-\d{4}$/;
		var timeRegex = /^\d{4}$/;
		var numRegex = /^[0-9]/;
		
		
		//기존비밀번호 일치하는지 확인.
		var pw = document.getElementById('passwordinput').value;
		var hno = document.getElementById('hno').value;
		var result = "false";
		$.ajax({
			type: 'post',
			url: '/HopInfoPWCheck',
			data: { pw: pw, hno : hno},
			dataType : 'text',
			success: function(data) {
				result = data;
				
				if(result == "false"){
					alert("비밀번호가 틀렸습니다.")
					return;
				}else if($("#pwcheck").text() != "일치"){
					alert("새 비밀번호를 입력하거나 일치하는 것을 확인해야합니다. ");
					$("#newpwcheck").focus();
				}else if (cnt == 0) {
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
				}else if($("#num_mem").val() == null){
					alert("시간별 예약인원을 입력해주세요.");
					$("#num_mem").focus();
					return;
				}else if(!numRegex.test($("#num_mem").val())){
					alert("시간별 예약인원을 숫자로 입력해주세요.")
					$("#num_mem").focus();
				}
				
				$("#hospitalFrm").submit();
			},
			error: function(request, status, error) {
				console.log("실패..");
				console.log("code : " + request.status);
				console.log("message : " + request.responseText);
				console.log("error : " + error);
			},
			complete: function() {
				//console.log("완료");
				//console.log("result : " + result);
						
			},
			beforeSend: function() {
				//console.log("이제 보낼거다..")
			}
		});


	});
	
})






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
