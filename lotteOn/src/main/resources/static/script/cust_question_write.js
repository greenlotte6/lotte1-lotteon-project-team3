
    document.addEventListener("DOMContentLoaded", function () {
    const category1 = document.getElementById("type");
    const category2 = document.getElementById("sub");

    const subCategories = {
    member: ["가입", "탈퇴", "회원정보", "로그인"],
    event: ["쿠폰/할인혜택", "포인트", "제휴", "이벤트"],
    order: ["상품", "결제", "구매내역", "영수증/증빙"],
    deliver: ["배송상태/기간", "배송정보확인/변경", "해외배송", "당일배송", "해외직구"],
    cancel: ["반품신청/철회", "반품정보확인/변경", "교환 AS신청/철회", "교환정보확인/변경", "취소신청/철회", "취소확인/환불정보"],
    travel: ["예약확인", "변경/취소"],
    safe: ["서비스 이용규칙 위반", "지식 재산권 침해", "법령 및 정책위반 상품", "게시물 정책위반", "직거래/외부거래유도", "표시광고", "청소년 위해상품/이미지"]
};

    category1.addEventListener("change", function () {
    const selected = this.value;

    // 2차 카테고리 초기화
    category2.innerHTML = '<option value="default">2차 카테고리</option>';

    // 선택한 1차 항목에 맞는 2차 옵션 생성
    if (subCategories[selected]) {
    subCategories[selected].forEach(item => {
    const option = document.createElement("option");
    option.value = item;
    option.textContent = item;
    category2.appendChild(option);
});
}
});
});
