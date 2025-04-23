
document.addEventListener('DOMContentLoaded', function () {
    const typeSelects = document.querySelectorAll('.typeSelect');

    const detailOptions = {
        member: ['가입', '탈퇴', '회원정보', '로그인'],
        event: ['쿠폰/할인혜택', '포인트', '제휴', '이벤트'],
        order: ['상품', '결제', '구매내역', '영수증/증빙'],
        delivery: ['배송상태/기간', '배송정보확인/변경', '해외배송', '당일배송', '해외직구'],
        cancel: ['반품신청/철회', '반품정보확인/변경', '교환AS신청/철회', '교환정보확인/변경', '취소신청/철회', '취소확인/환불정보'],
        travel: ['여행/숙박', '항공'],
        safe: ['서비스 이용규칙 위반', '지식재산권침해', '법령 및 정책위반 상품', '게시물 정책위반', '직거래/외부거래유도', '표시광고', '청소년 위해상품/이미지']
    };

    typeSelects.forEach(typeSelect => {
        typeSelect.addEventListener('change', function () {
            // 같은 부모(.selectPair) 안에 있는 detailSelect 찾기 (메인 + 모달 모두 대응)
            const wrapper = typeSelect.closest('.selectPair') || typeSelect.parentElement;
            const detailSelect = wrapper.querySelector('.detailSelect');

            const selected = this.value;

            if (detailSelect) {
                detailSelect.innerHTML = '<option value="">세부유형</option>';

                if (detailOptions[selected]) {
                    detailOptions[selected].forEach(item => {
                        const opt = document.createElement('option');
                        opt.value = item;
                        opt.textContent = item;
                        detailSelect.appendChild(opt);
                    });
                }
            }
        });
    });
});
