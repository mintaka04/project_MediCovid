//모달부분
function setMember(obj, date, time, revNum){
	console.log(obj.id);
	$('#hiddenforid').val(date+time);
	$('#hiddenfordate').val(date);
	$('#hiddenfortime').val(time);
	
	//option 예약인원이상만 선택되도록
	for(var i = 0; i <= 12; i++){
		if(i < revNum){
			//해당opt 비활성화.....
			document.getElementById("opt"+i).disabled = true;
		}
	}
	
	
	
	document.getElementById("mini_date").innerHTML = date+"       "+time;
	document.getElementById("mini_revNum").innerHTML = "현재 예약중인 환자 : " + revNum;	
	document.querySelector('.rev_modal').style.display = 'block';
	document.querySelector('.back_modal').style.display = 'block';
}

function modalclose(){
	document.querySelector('.rev_modal').style.display = 'none';
	document.querySelector('.back_modal').style.display = 'none';
	
}




//인원선택후 컨트롤러로 넘겨주기
function confirm(id, date, time, hno){
	
	//예약총인원수
	var d = document.getElementById("num");
	var selNum = d.options[d.selectedIndex].value;
	
	//ajax 결과후 반영할 영역의 id
	var id = "btn_"+id;


	$.ajax({
	type: 'post',
	url : '/HopChangeRevNum',
	dataType : 'text',
	data : {selNum : selNum, date : date, time : time, hno : hno},
	success : function(data){
		
		//console.log("성공!");
		//버튼 바꿔주기. -> 간단해서 여기다 이미지태그랑 인원수만 넣어주면 될것 같은데.
		document.getElementById(id).innerHTML = "<img class='timg' src='../images/user.png' alt='' />"+selNum+"명";
		document.querySelector('.rev_modal').style.display = 'none';
		document.querySelector('.back_modal').style.display = 'none';
	},
	error : function(request, status, error){
		console.log("실패..");
		console.log("code : "+request.status);
		console.log("message : " + request.responseText);
		console.log("error : " + error);
	},
	complete : function(){
		//console.log("완료");
	},
	beforeSend : function(){
		//console.log("이제 보낼거다..")
	}
});
}