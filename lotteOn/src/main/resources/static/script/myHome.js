
document.addEventListener("DOMContentLoaded", function(){
    //주문번호  모달 요소
    const orderLinks= document.querySelectorAll('.order-link');
    const modal= document.getElementById('orderModal');
    const closeBtn = document.querySelector('.close');

    //상호명 모달 요소
    const companyLinks= document.querySelectorAll('.company-link');
    const companyModal= document.getElementById('companyModal');

    //문의하기 모달 요소
    const inquiryBtn = document.querySelector('#companyModal button'); // 회사 모달 내 문의하기 버튼
    const inquiryModal = document.getElementById('inquiryModal');
    const inquiryCloseBtn = inquiryModal.querySelector('.inquiry-close');

    //구매확정 모달 요소
    const confirmModal = document.getElementById('confirmModal');
    const confirmCloseBtn = confirmModal.querySelector('.close');
    const confirmButtons= document.querySelectorAll('.confirm-btn');

    //상품평쓰기 모달 요소
    const reviewModal = document.getElementById('reviewModal');
    const reviewCloseBtn= reviewModal.querySelector('.close');
    const reviewButtons = document.querySelectorAll('.review-btn');

    //반품신청 모달 요소
    const returnModal= document.getElementById('returnModal');
    const returnCloseBtn= returnModal.querySelector('.close');
    const returnButtons = document.querySelectorAll('.return-btn');

    //교환신청 모달 요소
    const exchangeModal = document.getElementById('exchangeModal');
    const exchangeCloseBtn= exchangeModal.querySelector('.close');
    const exchangeButtons = document.querySelectorAll('.exchange-btn');


    //문의하기 버튼 클릭 시 모달 열기
    inquiryBtn.addEventListener('click', function () {
        inquiryModal.style.display = "block";
        companyModal.style.display= "none";
    });

    //구매확정 클릭 시 모달 열기
    confirmButtons.forEach(button =>{
        button.addEventListener('click', function () {
            confirmModal.style.display= "block";
        });
    });

    //상품평쓰기 클릭 시 모달 열기
    reviewButtons.forEach(button =>{
        button.addEventListener('click', function () {
            reviewModal.style.display= "block";
        });
    });

    //반품신청 클릭 시 모달 열기
    returnButtons.forEach(button =>{
        button.addEventListener('click', function () {
            returnModal.style.display= "block";
        });
    });

    //교환신청 클릭 시 모달 열기
    exchangeButtons.forEach(button =>{
        button.addEventListener('click', function () {
            exchangeModal.style.display= "block";
        });
    });


    //주문 모달 닫기
    closeBtn.addEventListener('click', function(){
        modal.style.display= "none"; //모달 숨기기
    });

    //상호명 모달 닫기
    companyCloseBtn.addEventListener('click', function (){
        companyModal.style.display= "none";
    });

    //문의 모달 닫기
    inquiryCloseBtn.addEventListener('click', function () {
        inquiryModal.style.display= "none";
        companyModal.style.display= "none";
    });

    //구매확정 모달 닫기
    confirmCloseBtn.addEventListener('click', function () {
        confirmModal.style.display= "none";
    });

    //상품평 모달 닫기
    reviewCloseBtn.addEventListener('click', function () {
        reviewModal.style.display= "none";
    });

    //반품신청 모달 닫기
    returnCloseBtn.addEventListener('click', function () {
        returnModal.style.display= "none";
    });

    //교환신청 모달 닫기
    exchangeCloseBtn.addEventListener('click', function () {
        exchangeModal.style.display="none";
    });


    //모달 바깥 영역 클릭 시 모달 닫기
    // document.querySelectorAll('.modal').forEach(modal => {
    //     modal.addEventListener('click', function(e) {
    //         // 'modal' 클래스 요소 자체를 클릭했을 때만 닫기
    //         if (e.target.classList.contains('modal')) {
    //             modal.style.display = "none";
    //         }
    //     });
    // });



});