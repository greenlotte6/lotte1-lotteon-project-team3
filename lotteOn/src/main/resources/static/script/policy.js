    document.addEventListener("DOMContentLoaded", function () {
        const menuItems = document.querySelectorAll(".sub_menu li");
        const contents = document.querySelectorAll(".tab_content");

        menuItems.forEach(item => {
            item.addEventListener("click", function (e) {
                e.preventDefault();

                // 메뉴 선택 표시 초기화
                menuItems.forEach(i => i.classList.remove("selected"));
                this.classList.add("selected");

                // 콘텐츠 표시 전환
                const targetId = this.getAttribute("data-target");
                contents.forEach(content => {
                content.style.display = (content.id === targetId) ? "block" : "none";
            });
        });
    });
});
    document.addEventListener("DOMContentLoaded", function () {
        const printBtn = document.getElementById("printBtn");
        const tabContents = document.querySelectorAll(".tab_content");

            printBtn.addEventListener("click", function () {
            // 현재 보이는 탭만 출력
                tabContents.forEach(tab => {
                if (tab.style.display !== "none") {
                const originalContent = document.body.innerHTML;
                document.body.innerHTML = tab.innerHTML;
                window.print();
                document.body.innerHTML = originalContent;
                location.reload(); // 프린트 후 원래 페이지로 복구
            }
        });
    });

});