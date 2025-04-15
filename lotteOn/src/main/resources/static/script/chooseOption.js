document.addEventListener('DOMContentLoaded', function() {
    const chooseBtn = document.querySelector(".chooseBtn");
    const options = document.querySelector(".options");

    chooseBtn.addEventListener("click", function(event) {
        event.stopPropagation(); // 이벤트 전파 막기
        options.classList.toggle("open");
    });

    document.body.addEventListener("click", function() {
        options.classList.remove("open");
    });

    // 옵션창 클릭 시에도 닫히지 않게 (선택)
    options.addEventListener("click", function(event) {
        event.stopPropagation();
    });
});
