//약국 swiper 관련
var swiper = new Swiper(".mySwiper", {
        slidesPerView: 3,
        spaceBetween: 30,
        slidesPerGroup: 3,
        loop: true,
        loopFillGroupWithBlank: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
});






//지도 이동
function moveToMap(px, py){
	console.log("눌림 : " + px +", "+py);

	var ret = window.open("/pharmMap?py="+py+"&px="+px, "_blank", "width=800,height=600");
	
}