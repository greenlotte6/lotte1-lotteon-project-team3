
document.addEventListener("DOMContentLoaded", function(){
    //주문번호  모달 요소
    const orderLinks= document.querySelectorAll('.order-link');
    const modal= document.getElementById('orderModal');
    const closeBtn = document.querySelector('.close');

    //상호명 모달 요소
    const companyLinks= document.querySelectorAll('.company-link');
    const companyModal= document.getElementById('companyModal');
    const companyCloseBtn= companyModal.querySelector('.close');

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

    //주문 상세 모달 열기
    orderLinks.forEach(link =>{
        link.addEventListener('click', function(e){
            e.preventDefault(); //기본 링크 클릭 동작 방지
            modal.style.display = "block"; //모달 표시
        });
    });

    //상호명 클릭 시 모달 열기
    companyLinks.forEach(link =>{
        link.addEventListener('click', function (e){
            e.preventDefault();
            companyModal.style.display="block";
        });
    });

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
    window.addEventListener('click', function(e){
        if(e.target == modal){
            modal.style.display = "none";
        }else if(e.target == companyModal){
            companyModal.style.display ="none";
        }else if(e.target== inquiryModal){
            inquiryModal.style.display= "none";
        }else if(e.target== confirmModal){
            confirmModal.style.display= "none";
        }else if(e.target== reviewModal){
            reviewModal.style.display= "none";
        }else if(e.target== returnModal){
            returnModal.style.display= "none";
        }else if(e.target== exchangeModal){
            exchangeModal.style.display="none";
        }
    });

});