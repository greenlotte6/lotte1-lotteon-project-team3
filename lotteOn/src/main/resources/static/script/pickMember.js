document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("bannerModal");
    const closeBtn = document.querySelector(".modal-content .close");

    // '수정' 버튼 클릭 이벤트
    document.querySelectorAll("button.modify-btn").forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();

            const row = this.closest("tr");
            const memberData = row.dataset;

            // 모달 안 input에 값 채워넣기
            document.querySelector("#bannerModal input[placeholder='아이디 입력']").value = memberData.id;
            document.querySelector("#bannerModal input[placeholder='이름 입력']").value = memberData.name;
            document.querySelector("#bannerModal input[placeholder='이메일 입력']").value = memberData.email;
            document.querySelector("#bannerModal input[placeholder='휴대전화 번호 입력']").value = memberData.hp;
            document.querySelector("#bannerModal input[placeholder='MVG']").value = memberData.rating;
            //document.querySelector("#bannerModal .modal-content div:contains('가입일')").textContent = memberData.regdate;

            // 성별 체크 (예시)
            const genderInputs = document.querySelectorAll(".gender");
            genderInputs.forEach(input => {
                input.checked = (input.nextSibling.textContent.trim() === memberData.gender);
            });

            // 모달 열기
            modal.style.display = "block";
        });
    });

    // 닫기 버튼
    closeBtn.onclick = function () {
        modal.style.display = "none";
    }
});