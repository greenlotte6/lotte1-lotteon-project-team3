document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("deleteBtn").addEventListener("click", function () {
        const checkedBoxes = document.querySelectorAll(".delete-check:checked");

        const bannerIds = Array.from(checkedBoxes).map(cb =>
            cb.closest("tr").dataset.id
        );

        console.log(bannerIds);

        // 선택된 항목이 없으면 경고
        if (bannerIds.length === 0) {
            alert("삭제할 항목을 선택하세요.");
            return;
        }

        // 서버로 삭제 요청 보내기
        fetch("/banners/delete", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(bannerIds)  // 선택된 버전 ID 리스트 전달
        })
            .then(res => {
                if (res.ok) {
                    alert("삭제되었습니다.");
                    location.reload();  // 삭제 후 페이지 새로고침
                } else {
                    alert("삭제 실패");
                }
            })
            .catch(err => {
                console.error(err);
                alert("오류 발생");
            });
    });
});
