
//날짜 증가버튼 클릭시 호출
function datePlus() {
	//console.log("증가선택됨");
	var value = $('#datehidden').val();

	//숫자로 변환후 1 더해주기
	value = Number(value) + 1;

	dateSelect(value);
}


//날짜 감소버튼 클릭시 호출
function dateMinus() {
	//console.log("감소 선택됨");
	var value = $('#datehidden').val();

	value = Number(value) - 1;
	dateSelect(value);
}


//ajax 만들기..
//1. hidden값을 컨트롤러에 보내고
//2. 컨트롤러에서 출력할 리스트에 대해 jsp 파일을 만들고
//3. 그 jsp파일 코드를 가져와 해당부분에 대신 집어넣음.
function dateSelect(value) {
	//console.log("ajax 함수 들어옴");

	//hidden 값이 15될때까지. 즉, +15일까지만 조회 가능
	if (value <= 15 && value >= 0) {
		//console.log(value);
		$.ajax({

			type: 'post',
			url: '/HospitalMainDatePlus',
			data: { hiddendate: value },

			success: function(data) {

				//console.log("성공");
				//console.log(data);
				$('#region1').html(data);
				$('#datehidden').val(value);
			},
			error: function() {
				//console.log("실패..");
			},
			complete: function() {
				//console.log("완료");
			},
			beforeSend: function() {
				//console.log("이제 보낼거다..")
			}
		});
	}
}




//내방확인 버튼 클릭시
function buttonToggle(rno) {

	//클릭한 버튼의 value값 가져오기
	//console.log($('#btn' + rno).val());

	if ($('#btn' + rno).val() == "내방확인") {
		//내방확인상태일때만 동작할것. 즉, 현재 내방확인안되어있고, 여기서는 내방확인하려고함
		//console.log("환자가 내방했음");
		var visitstatus = "visited";

	} else {
		//내방확인상태 아닐때만 동작. 즉, 내방확인했던거 취소하려고 함.
		//console.log("내방 취소함");
		var visitstatus = "cancel";
	}

	$.ajax({
		type: 'get',
		url: '/HospitalVisitConfirm',
		data: { visitstatus: visitstatus, rno: rno },
		success: function() {

			if ($('#btn' + rno).val() == "내방확인") {
				$('#btn'+rno).val("내방취소");
			} else {
				$('#btn'+rno).val("내방확인");
			}
		},
		error: function() {
			//console.log("실패!");
		},
		complete: function() {
			//console.log("완료!");
		},
		beforeSend: function() {
			//console.log("ajax 시작!")
		}
	});

}




//과거기록 확인 필터 체크시 동작
function checkfilter(obj){
	
	//클릭한 버튼의 id값
	console.log(obj.id);
	
	$.ajax({
		type : 'post',
		url : '/HospitalPastResult',
		data : {pastday : obj.id},
		success : function(data){
			console.log("성공");
			$('#pastlistresult').html(data);
			
		},
		error: function() {
			console.log("실패!");
		},
		complete: function() {
			console.log("완료!");
		},
		beforeSend: function() {
			console.log("ajax 시작!")
		}
	});
}





//예약 인원수 수정 페이지로 가기
function gotoRevperTime(){
	location.href = "/HospitalRevPerTime";
}


function setInfo(){
	location.href = "/HospitalInfoSet";
}



