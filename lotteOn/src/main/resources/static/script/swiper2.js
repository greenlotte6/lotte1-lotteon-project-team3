document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper2', {
        direction: 'horizontal',
        slidesPerView: 5,
        spaceBetween: 40,
        navigation: false,
        loop: true,
        autoplay: {
            // 자동재생 여부
            delay: 3000, // 시작시간 설정
        }
    });
});