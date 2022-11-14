


/* 차트 번갈아가며 표시 */
$(document).ready(function() {
	$('.seoulChart').show(); //페이지를 로드할 때 표시할 요소
	$('.koreaChart').hide(); //페이지를 로드할 때 숨길 요소
	$('.koreaChartLabel').click(function() {
		$('.seoulChart').hide(); //클릭 시 첫 번째 요소 숨김
		$('.koreaChart').show(); //클릭 시 두 번째 요소 표시
		$('.seoulChartLabel').click(function() {
			$('.koreaChart').hide(); //클릭 시 두 번째 요소 표시
			$('.seoulChart').show(); //클릭 시 첫 번째 요소 숨김
			return false;
		});
	});
});

/* koreaChart */