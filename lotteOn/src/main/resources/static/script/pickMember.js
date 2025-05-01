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
            document.querySelector("#bannerModal input[name='id']").value = memberData.id;
            document.querySelector("#bannerModal input[name='name']").value = memberData.name;
            document.querySelector("#bannerModal select[name='rating']").value = memberData.rating;
            document.querySelector("#bannerModal input[name='email']").value = memberData.email;
            document.querySelector("#bannerModal input[name='hp']").value = memberData.hp;
            document.querySelector("#bannerModal .memberStatus").textContent = memberData.status;
            document.querySelector("#bannerModal .birthDate").textContent = memberData.birthdate;
            document.querySelector("#bannerModal input[name='zip']").value = memberData.zip;
            document.querySelector("#bannerModal input[name='addr1']").value = memberData.addr1;
            document.querySelector("#bannerModal input[name='addr2']").value = memberData.addr2;
            document.querySelector("#bannerModal input[name='another']").value = memberData.another;
            document.querySelector("#bannerModal .regDate").textContent = memberData.regdate;
            document.querySelector("#bannerModal .visitDate").textContent = memberData.visitdate;
            console.log(memberData.another);
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