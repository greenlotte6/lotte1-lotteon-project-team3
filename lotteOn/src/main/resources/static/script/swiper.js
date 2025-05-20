document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper', {
        slidesPerView: 3,
        slidesPerGroup: 3,
        direction: 'horizontal',
        spaceBetween: 20,
        autoplay: {
            // 자동재생 여부
            delay: 5000, // 시작시간 설정
        },
        loop: true,
        navigation: false
    });
});