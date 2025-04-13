document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper', {
        // Optional parameters
        direction: 'horizontal',
        autoplay: {
            // 자동재생 여부
            delay: 5000, // 시작시간 설정
        },
        loop: true
    });
});