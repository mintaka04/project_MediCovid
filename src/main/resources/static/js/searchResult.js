$(function() {
	var hx = [];
	var hy = [];
	var hname = [];

	$("input[name='hx']").each(function(index, item) {
		hx.push($(item).val());
	});

	$("input[name='hy']").each(function(index, item) {
		hy.push($(item).val());
	});

	$(".hospitalName").each(function(index, item) {
		hname.push($(item).text());
	});

	// 검색 결과 페이지 호출되면 바로 카카오맵 불러옴
	kakaoMap(hx, hy, hname);

	// 페이지 숫자 버튼 클릭
	$(".paginationDiv .pagination .pageButton").on("click", function() {
		var cp = $(this).text();

		pageMoving(cp);
	});

	// 페이지 <이전 버튼 클릭
	$(".paginationDiv .pagination .prev").on("click", function() {
		var cp = parseInt($(".currentPage").text()) - 10;

		if (cp < 1) {
			cp = 1;
		}

		pageMoving(cp);
	});

	// 페이지 >이후 버튼 클릭
	$(".paginationDiv .pagination .next").on("click", function() {
		var cp = parseInt($(".currentPage").text()) + 10;
		var ttlPage = $(".ttlPage").val();
		
		if (cp > ttlPage) {
			cp = ttlPage;
		}
		
		pageMoving(cp);
	});
	
});

function pageMoving(cp) {
	var category = [];
	var gu = [];
	var keyword = $("#keyword").val();
	var url = "hospitals?cp=" + cp;

	$("input[name='category']:checked").each(function(index, item) {
		category.push($(item).val());
	});

	$("input[name='gu']:checked").each(function(index, item) {
		gu.push($(item).val());
	});

 	// category, keyword, gu 값이 있을때만(사용자가 조건을 추가했을 때만) 페이지 이동시 url에 파라미터로 추가 => 페이지 이동시에도 검색 조건 끌고 가기 위해서
	if (category != null || category != "") {
		url += "&category=" + category;
	}
	if (keyword != null || keyword != "") {
		url += "&keyword=" + keyword;
	}
	if (gu != null || gu != "") {
		url += "&gu=" + gu;
	}
	location.href = url;
}

function kakaoMap(hx, hy, hname) {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
		mapOption = {
			center: new kakao.maps.LatLng(hy[0], hx[0]), // 지도의 중심좌표(페이지 가장 첫번째 뜨는 병원의 좌표값)
			level: 8 // 지도의 확대 레벨
		};

	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

	// 마커를 표시할 위치와 title 객체 배열입니다 
	var positions = new Array();

	// 페이지에 뜨는 병원 6개의 병원명과 좌표값을 json 타입으로 만들어 배열 positions 에 넣음 
	for (var i = 0; i < hname.length; i++) {
		var hospital = new Object();

		hospital.title = hname[i];
		hospital.latlng = new kakao.maps.LatLng(hy[i], hx[i]);

		positions.push(hospital);
	}

	// 마커 이미지의 이미지 주소입니다
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

	for (var i = 0; i < positions.length; i++) {

		// 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(24, 35);

		// 마커 이미지를 생성합니다    
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map: map, // 마커를 표시할 지도
			position: positions[i].latlng, // 마커를 표시할 위치
			title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
			image: markerImage // 마커 이미지
		});
	}
}