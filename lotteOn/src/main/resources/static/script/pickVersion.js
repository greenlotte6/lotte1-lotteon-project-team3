document.addEventListener("DOMContentLoaded", function () {
    const subModal = document.getElementById("subModal");
    const closeBtn = document.querySelector(".submodal-content .close");

    document.querySelectorAll("button.submitbtn").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();

            const row = this.closest("tr");
            const versionData = row.dataset;

            document.querySelector(".thisVersion").innerText = versionData.versionid;
            document.querySelector(".thisContent").innerText = versionData.versioncontent;

            // 모달 열기
            subModal.style.display = "block";
        });
    });

    // 닫기 버튼
    closeBtn.onclick = function () {
        subModal.style.display = "none";
    }
});