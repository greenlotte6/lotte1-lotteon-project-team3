document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('.modalbtn button').addEventListener('click', () => {
        const dto = {
            name: document.querySelector('input[placeholder="상품명 입력"]').value,
            description: document.querySelector('input[placeholder="기본설명 입력"]').value,
            maker: document.querySelector('input[placeholder="제조사 입력"]').value,
            price: parseInt(document.querySelector('input[placeholder="금액 입력"]').value),
            discount: parseInt(document.querySelector('input[placeholder="할인율 입력"]').value),
            point: parseInt(document.querySelector('input[placeholder="포인트 입력"]').value),
            stock: parseInt(document.querySelector('input[placeholder="재고량 입력"]').value),
            deliveryFee: parseInt(document.querySelector('input[placeholder="배송비 입력"]').value),
            // 🔥 여기 추가 (1차/2차 중 최종 카테고리 선택된 값)
            categoryId: parseInt(document.querySelector('#category2').value), // 또는 category1

            // 🔥 판매자명 (companyName)
            companyName: document.querySelector('input[placeholder="판매자명 입력"]').value,

            imageList: "list.jpg",
            imageMain: "main.jpg",
            imageDetail: "detail.jpg",
            options: [
                {
                    optionName: document.querySelector('input[placeholder="옵션1 입력"]').value,
                    optionValue: document.querySelector('input[placeholder="옵션1 항목 입력"]').value
                },
                // 나머지 옵션들 추가 가능
            ],
            notice: {
                prodStatus: document.querySelector('input[placeholder="상품상태 입력"]').value,
                vatYn: document.querySelector('input[placeholder="부가세 면세여부 입력"]').value,
                receiptYn: document.querySelector('input[placeholder="영수증 발행 입력"]').value,
                businessType: document.querySelector('input[placeholder="사업자 구분 입력"]').value,
                origin: document.querySelector('input[placeholder="원산지 입력"]').value
            }
        };

        fetch('/admin/product/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto)
        })
            .then(res => res.text())
            .then(msg => {
                alert(msg);
                location.reload(); // 등록 후 새로고침 (필요 시)
            });
    });

});