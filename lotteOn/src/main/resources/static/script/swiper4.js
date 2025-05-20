document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper4', {
        direction: 'vertical',
        slidesPerView: 1,
        autoplay: {
            delay: 4000,
        },
        loop: true,
        allowTouchMove: false,
        navigation: false
    });
});