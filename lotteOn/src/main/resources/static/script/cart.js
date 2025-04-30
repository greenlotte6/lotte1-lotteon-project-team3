document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.control').forEach(control => {
        const minusBtn = control.querySelector('.minus');
        const plusBtn = control.querySelector('.plus');
        const countDiv = control.querySelector('.count');
        const cartId = minusBtn.dataset.id;

        const itemBox = control.closest('.cartItem');
        const priceEl = itemBox.querySelector('.itemPrice');

        minusBtn.addEventListener('click', () => {
            let quantity = parseInt(countDiv.textContent);
            if (quantity > 1) {
                updateQuantity(cartId, quantity - 1, countDiv, priceEl);
            }
        });

        plusBtn.addEventListener('click', () => {
            let quantity = parseInt(countDiv.textContent);
            updateQuantity(cartId, quantity + 1, countDiv, priceEl);
        });
    });

    function updateQuantity(cartId, newQty, countDiv, priceEl) {
        fetch('/product/cart/updateQuantity', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({cartId, quantity: newQty})
        })
            .then(res => res.json())
            .then(data => {
                console.log('응답 데이터:', data);
                console.log('응답 데이터:', data);
                console.log('응답 데이터:', data);
                console.log('응답 데이터:', data);
                const {price, discount, deliveryFee, updatedQuantity} = data;

                // 수량 반영
                countDiv.textContent = updatedQuantity;

                // 할인된 단가 및 총액
                const discountedUnitPrice = Math.floor(price * (100 - discount) / 100);
                const totalPrice = discountedUnitPrice * updatedQuantity;

                // data-* 동기화
                priceEl.setAttribute('data-price', price.toString());
                priceEl.setAttribute('data-discount', discount.toString());
                priceEl.setAttribute('data-delivery', deliveryFee.toString());
                priceEl.setAttribute('data-quantity', updatedQuantity.toString());

                // 화면에 표시
                priceEl.textContent = formatNumber(totalPrice) + '원';

                recalculateTotal();
            });
    }

    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

    function recalculateTotal() {
        let productTotal = 0;
        let discountTotal = 0;
        let deliveryTotal = 0;

        document.querySelectorAll('.itemPrice').forEach(el => {
            console.log('===');
            console.log('price:', el.getAttribute('data-price'));
            console.log('discount:', el.getAttribute('data-discount'));
            console.log('qty:', el.getAttribute('data-quantity'));
            console.log('delivery:', el.getAttribute('data-delivery'));

            const qty = parseInt(el.getAttribute('data-quantity') || '0');
            const price = parseInt(el.getAttribute('data-price') || '0');
            const discount = parseInt(el.getAttribute('data-discount') || '0');
            const delivery = parseInt(el.getAttribute('data-delivery') || '0');

            if (isNaN(qty) || isNaN(price) || isNaN(discount) || isNaN(delivery)) {
                console.warn('NaN 발생! ->', el.getAttribute);
                return;
            }

            const baseTotal = price * qty;
            const discountAmount = Math.floor(price * (discount / 100)) * qty;

            productTotal += baseTotal;
            discountTotal += discountAmount;
            deliveryTotal += delivery;
        });

        const finalTotal = productTotal - discountTotal + deliveryTotal;

        document.querySelector('.priceInfoBox .priceInfos:nth-child(2) .f-w-600').textContent = formatNumber(productTotal) + '원';
        document.querySelector('.priceInfoBox .priceInfos:nth-child(3) .f-w-600').textContent = '-' + formatNumber(discountTotal) + '원';
        document.querySelector('.priceInfoBox .priceInfos:nth-child(4) .f-w-600').textContent = formatNumber(deliveryTotal) + '원';
        document.querySelector('.totalPrice p:last-child').textContent = formatNumber(finalTotal) + '원';
        document.querySelector('.orderBtn span').textContent = formatNumber(finalTotal) + '원 주문하기';
    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 수량증가, 가격 js 끝 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 삭제기능 시작 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    //x클릭시 삭제
    document.querySelectorAll('.remove').forEach(button => {
        button.addEventListener('click', () => {
            const cartId = button.dataset.id;
            if (confirm('이 상품을 장바구니에서 삭제할까요?')) {
                fetch(`/product/cart/delete/${cartId}`, {
                    method: 'DELETE'
                })
                    .then(res => {
                        if (res.ok) {
                            location.reload(); // 새로고침
                        }
                    });
            }
        });
    });

    // 체크박스 전체선택/해제
    document.querySelector('.checkAll').addEventListener('change', function () {
        const checked = this.checked;
        document.querySelectorAll('.checkOne').forEach(chk => {
            chk.checked = checked;
        });
    });

// 개별 체크박스 변경 시 전체선택 상태 동기화
    document.querySelectorAll('.checkOne').forEach(chk => {
        chk.addEventListener('change', () => {
            const all = document.querySelectorAll('.checkOne');
            const checked = document.querySelectorAll('.checkOne:checked');

            document.querySelector('.checkAll').checked = all.length === checked.length;
        });
    });

// 선택삭제 버튼
    document.querySelector('.chooseRemove').addEventListener('click', () => {
        const checked = document.querySelectorAll('.checkOne:checked');
        if (checked.length === 0) {
            alert('삭제할 상품을 선택하세요.');
            return;
        }

        if (!confirm('선택한 상품을 삭제할까요?')) return;

        const ids = Array.from(checked).map(chk => Number(chk.dataset.id)); // ← Number로 변환 중요!

        fetch('/product/cart/deleteSelected', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(ids)
        }).then(res => {
            if (res.ok) {
                location.reload();
            }
        });
    });
});
