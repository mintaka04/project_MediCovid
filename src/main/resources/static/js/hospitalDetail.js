window.onload=function(){
	var tt = document.getElementById('hitime').innerText;
	var time = tt.substring(0, 2) + ":" + tt.substring(2,4)+" ~ "+tt.substring(4,6)+":"+tt.substring(6,8);
	document.getElementById('hitime_time').innerText = time;
	
}


function makeRev(){
	console.log("예약하기 버튼 눌림");
	console.log(document.getElementById('access_right').value);
	if(document.getElementById('access_right').value == "권한없음"){
		console.log("로그인페이지로이동")
		location.href="loginForm";
	}else{
		console.log("예약 창 띄우기");
		document.querySelector('.rev_modal').style.display = 'block';
		document.querySelector('.back_modal').style.display = 'block';
	}
}


function modalclose(){
	document.querySelector('.rev_modal').style.display = 'none';
	document.querySelector('.back_modal').style.display = 'none';
	
}

function gotoSearch(){
	var word = document.getElementById('searchinput').value;
	console.log(word);
	location.href="search/hospitals?keyword="+word;
}


//지도 관련
var hx = document.getElementById('mapX').value;
var hy = document.getElementById('mapY').value;

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center: new kakao.maps.LatLng(hy, hx), // 지도의 중심좌표
		level: 3, // 지도의 확대 레벨
		mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
	};

// 지도를 생성한다 
var map = new kakao.maps.Map(mapContainer, mapOption);

//지도에 마커 생성
var marker = new kakao.maps.Marker({
	position: new kakao.maps.LatLng(hy, hx), // 마커의 좌표
	map: map // 마커를 표시할 지도 객체
});

var hopName = document.getElementById('hop_name').innerText;

// 마커 위에 표시할 인포윈도우를 생성한다
var infowindow = new kakao.maps.InfoWindow({
	content: '<div style="padding:5px;">'+hopName+'</div>' // 인포윈도우에 표시할 내용
});

// 인포윈도우를 지도에 표시한다
infowindow.open(map, marker);






function select_date(obj, htime, hno){
	//obj : 8월 4일 형태.
	console.log("날짜 선택됨");
	console.log(obj);
	console.log("병원운영시간 : " + htime);
	//ajax 써서 obj를 보내고 해당 날짜에 대한 예약인원들을 계산,
	//계산한 값들을 시간당 예약가능인원들과 비교해 
	//예약인원수가 예약가능인원과 같거나 크다면 해당 위치의 값을 0으로 한걸 리턴해 받아오기...
	
	$.ajax({
		type : 'GET',
		url : '/ajaxRevTime',
		data : {selectedDate : obj, htime : htime, hno:hno},
		dataType : 'json',
		success : function(data){
			console.log("성공");
			console.log(data.posRvNum);

			for(var i = 0; i < data.posRvNum.length; i++){
				//console.log("i값입니다 : " + i);
				//console.log("해당 배열값입니다 : " + data.posRvNum[i]);
				if(data.posRvNum[i] < 1){
					//해당 시간대 li값...
					console.log(document.getElementById(i).innerHTML);
					document.getElementById(i).style.pointerEvents = 'none';		
					document.getElementById(i).style.textDecoration = 'line-through';		
				}else{
					document.getElementById(i).style.pointerEvents = 'auto';									
					document.getElementById(i).style.textDecoration = 'none';		
				}
			}
		},
		error : function(){
			console.log("실패..");
		},
		complete : function(){
			console.log("완료");
			
		},
		beforeSend : function(){
			console.log("이제 보낼거다..")
		}
	});
	
}





//진짜 예약하기
function addResv(hno){
	//console.log("예약날짜 : "+document.getElementById('dropdown_date').innerHTML);
	//console.log("예약시간 : "+document.getElementById('dropdown_time').innerHTML);
	//console.log("예약자명 : "+document.getElementById('name_ul').value);
	//console.log("연락처 : "+document.getElementById('phone_ul').value);
	//console.log("특이사항 : "+document.getElementById('modal_text_input_desc').value);
	
	var date = document.getElementById('dropdown_date').innerHTML;
	var time = document.getElementById('dropdown_time').innerHTML;
	var name = document.getElementById('name_ul').value;
	var tel = document.getElementById('phone_ul').value;
	var desc = document.getElementById('modal_text_input_desc').value;
	
	var numRegex = /^[0-9]{9,11}$/;
	var nameRegex = /^[a-zA-Z가-힣]+$/;
	
	if(date == "예약 날짜"){
		alert("예약 날짜를 선택해주세요.");
		return;
	}else if(time == "예약 시간"){
		alert("예약 시간을 선택해주세요.");
		return;
	}else if(name == null){
		alert("이름을 입력해주세요.");
		return;
	}else if(!nameRegex.test($("#name_ul").val())){
		alert("실명을 입력해주세요.");
		return;
	}else if(tel == null){
		alert("연락처를 입력해주세요.");
		return;
	}else if(!numRegex.test($("#phone_ul").val())){
		console.log("전 : "+$("#phone_ul").val());
		alert("전화번호를 형식에 맞춰 입력헤주세요.");
		return;
	}
	
	console.log("유효성검사 통과!");
	
	$.ajax({
		type: 'get',
		url: '/MakeReservation',
		data: { rdate : date, rtime : time, rname : name, rtel : tel, rremark : desc, hno : hno},
		success: function() {
			console.log("성공!");
		},
		error: function() {
			//console.log("실패!");
		},
		complete: function() {
			//console.log("완료!");
			alert("예약이 완료되었습니다.");
			location.href="Hospital?hno="+hno;
		},
		beforeSend: function() {
			//console.log("ajax 시작!")
		}
	});
}




$(function(){
	//셀렉트박스	
	
	var _select_title = $("#dropdown_date");	 //변수저장
	$('<div class="select_icon"></div>').insertAfter(_select_title); //셀렉트박스 세모 아이콘 넣어줄 div
	var _select_title2 = $("#dropdown_time");
	$('<div class="select_icon2"></div>').insertAfter(_select_title2);
	
	_select_title.click(function () { //클릭시 ul, li 보여주기. 셀렉트박스 세모 아이콘 클릭시 방향 바꿔주기
	  $("#date_ul").toggleClass("active");
	  $(".select_icon").toggleClass("active");
	});
	_select_title2.click(function () { //클릭시 ul, li 보여주기. 셀렉트박스 세모 아이콘 클릭시 방향 바꿔주기
	  $("#time_ul").toggleClass("active");
	  $(".select_icon2").toggleClass("active");
	});
	
	
	$("#date_ul > li").on('click', function () { //li클릭하면	  
		var _li_value = $(this).text();  //li의 텍스트를 가지고 온다  
		_select_title.text(_li_value);   //저장해둔 _select_title변수의 text값을 클릭한 li text값으로 바꾼다.		
		$(".ul_select_style").removeClass("active");  //ul, li를 다시 닫아주고
		$(".select_icon").toggleClass("active");  //세모 모양 방향도 바꿔줌.
	});
	$("#time_ul > li").on('click', function () { //li클릭하면
		var _li_value = $(this).text();  //li의 텍스트를 가지고 온다  
		_select_title2.text(_li_value);   //저장해둔 _select_title변수의 text값을 클릭한 li text값으로 바꾼다.		
		$(".ul_select_style").removeClass("active");  //ul, li를 다시 닫아주고
		$(".select_icon2").toggleClass("active");  //세모 모양 방향도 바꿔줌.
	});
	
	//select 박스처럼 select 영역 외에 영역 클릭시에도 닫히게 만들기. 즉,  body 영역 클릭시
	$("body").click(function (e) {
	  if($(".ul_select_style").hasClass("active")){//ul, li가 active class를 가지고 있으면(즉, ul, li가 열려있으면)
	    if(!$(".text_ul_wrap").has(e.target).length){//이 영역 클릭할 때를 제외하고	
			$(".ul_select_style").removeClass("active");//ul, li를 닫아주고
			$(".select_icon").removeClass("active");  //세모 모양 방향 바꿔줌  
			$(".select_icon2").removeClass("active");  //세모 모양 방향 바꿔줌  
	    };
	  }
	})
})



