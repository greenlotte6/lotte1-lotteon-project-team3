document.addEventListener("DOMContentLoaded", () => {
    // 전체 선택 체크박스
    const selectAllCheckbox = document.getElementById("selectAll");

    // 모든 체크박스들
    const checkboxes = document.querySelectorAll(".shopCheckbox");

    // 전체 선택 클릭 시 모든 체크박스 상태 변경
    selectAllCheckbox.addEventListener("change", () => {
        checkboxes.forEach(cb => {
            cb.checked = selectAllCheckbox.checked;
        });
    });

    // 개별 선택 클릭 시 전체 선택 상태 업데이트
    checkboxes.forEach(cb => {
        cb.addEventListener("change", () => {
            // 모든 박스 선택된 경우 select 박스를 체크
            selectAllCheckbox.checked = [...checkboxes].every(checkbox => checkbox.checked);
        });
    });

    //선택삭제 버튼 클릭 시 선택된 체크박스의 값을 폼에 추가
    document.querySelector(".delbutton").addEventListener("click",(e)=>{
        const selectedIds = [...checkboxes]
            .filter(cb => cb.checked)
            .map(cb => cb.value);

        if (selectedIds.length===0){
            alert("삭제할 항목을 선택해주세요.");
            e.preventDefault();
        }else{
            const form = document.getElementById("deleteForm");
            const input = document.createElement("input");
            input.type="hidden";
            input.name="sellerIds";
            input.value= selectedIds.join(",");
            form.appendChild(input);
        }
    });

});
