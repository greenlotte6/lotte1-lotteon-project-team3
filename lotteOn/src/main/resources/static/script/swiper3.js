document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper', {
        direction: 'vertical',
        autoplay: {
            delay: 3500,
        },
        loop: true,
        allowTouchMove: false,
        navigation: false
    });
});