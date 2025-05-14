document.addEventListener('DOMContentLoaded', function() {
    const swiper = new Swiper('.swiper2', {
        direction: 'horizontal',
        slidesPerView: 5,
        spaceBetween: 40, 
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });
});