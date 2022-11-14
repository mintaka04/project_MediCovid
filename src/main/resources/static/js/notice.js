$(function() {
	// 글 작성
	$(".writeBtn").on("click", function() {
		$(".modal-title").text("글 작성");
		$(".modal-footer .writeOkBtn").attr("type", "button");
		$(".modal-footer .editBtn, .modal-footer .deleteOkBtn, .modal-footer .editOkBtn").attr("type", "hidden");
		$("form").attr("action", "board").attr("method", "post");
		$(".titleDiv input[name='title']").removeClass("titleDisable").prop("disabled", false).val("");
		$(".contentDiv textarea").prop("disabled", false).text("");
		$(".modal").show();
	});

	// 글 보기	
	$("tr.clickable").on("click", function() {
		let bno = $(this).children("td")[0].innerText;

		$.ajax({
			method: "GET",
			url: "/board/" + bno,
			dataType: "json",
			success: function(result) {
				$(".titleDiv input[name='title']").val(result.title);
				$(".contentDiv textarea").text(result.content);
			}
		});

		$(".modal-title").text("공지사항");
		$(".modal-footer .editBtn").attr("type", "button");
		$(".modal-footer .writeOkBtn, .modal-footer .deleteOkBtn, .modal-footer .editOkBtn").attr("type", "hidden");
		$(".titleDiv input[name='title']").addClass("titleDisable").prop("disabled", true);
		$(".contentDiv textarea").prop("disabled", true);
		$(".modal").show();

		// 글 수정창으로 변경
		$(".editBtn").on("click", function() {
			$(".modal-title").text("글 수정");
			$(".modal-footer .editOkBtn, .modal-footer .deleteOkBtn").attr("type", "button");
			$(".modal-footer .writeOkBtn,  .modal-footer .editBtn").attr("type", "hidden");
			$("form").attr("action", "board/" + bno).attr("method", "post");
			$(".titleDiv input[name='title']").removeClass("titleDisable").prop("disabled", false);
			$(".contentDiv textarea").prop("disabled", false);
			$(".modal").show();
		});

	});
	
	// 글 등록
	$(".writeOkBtn").on("click", function() {
		if ($("input[name='title']").val() == null || $("input[name='title']").val() == "") {
			alert("제목을 입력해주세요");
			$("input[name='title']").focus();
			return;
		} else if ($("textarea").val() == null || $("textarea").val() == "") {
			alert("내용을 입력해주세요");
			$("textarea").focus();
			return;
		}

		$("input[name='_method']").attr("value", "post");

		if (confirm("이대로 등록하시겠습니까?")) {
			$("form").submit();
		}
	});

	// 글 삭제
	$(".deleteOkBtn").on("click", function() {
		$("input[name='_method']").attr("value", "delete");

		if (confirm("이 공지를 정말 삭제하시겠습니까?")) {
			$("form").submit();
		}
	});

	// 글 수정
	$(".editOkBtn").on("click", function() {
		if ($("input[name='title']").val() == null || $("input[name='title']").val() == "") {
			alert("제목을 입력해주세요");
			$("input[name='title']").focus();
			return;
		} else if ($("textarea").val() == null || $("textarea").val() == "") {
			alert("내용을 입력해주세요");
			$("textarea").focus();
			return;
		}

		$("input[name='_method']").attr("value", "put");

		if (confirm("이대로 수정하시겠습니까?")) {
			$("form").submit();
		}
	});

	// 모달 닫기
	$("#closeBtn").on("click", function() {
		$(".modal").hide();
	});


})