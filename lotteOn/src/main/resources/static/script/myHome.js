document.addEventListener("DOMContentLoaded", function(){
    //주문번호 클릭 시 모달 열기
    const orderLinks= document.querySelectorAll('.order-link');
    const modal= document.getElementById('orderModal');
    const closeBtn = document.querySelector('.close');

    orderLinks.forEach(link =>{
        link.addEventListener('click', function(e){
            e.preventDefault(); //기본 링크 클릭 동작 방지
            modal.style.display = "block"; //모달 표시
        });
    });

    //모달 닫기 버튼 클릭 시
    closeBtn.addEventListener('click', function(){
        modal.style.display= "none"; //모달 숨기기
    });

    //모달 바깥 영역 클릭 시 모달 닫기
    window.addEventListener('click', function(e){
        if(e.target == modal){
            modal.style.display = "none";
        }
    });

});