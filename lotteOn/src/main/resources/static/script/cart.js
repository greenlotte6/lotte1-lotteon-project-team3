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
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cartId, quantity: newQty })
        })
            .then(res => res.json())
            .then(data => {
                const { price, discount, deliveryFee, updatedQuantity } = data;

                countDiv.textContent = updatedQuantity;

                const discountedUnitPrice = Math.floor(price * (100 - discount) / 100);
                const totalPrice = discountedUnitPrice * updatedQuantity;

                priceEl.setAttribute('data-price', price.toString());
                priceEl.setAttribute('data-discount', discount.toString());
                priceEl.setAttribute('data-delivery', deliveryFee.toString());
                priceEl.setAttribute('data-quantity', updatedQuantity.toString());

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

        document.querySelectorAll('.cartItem').forEach(item => {
            const checkbox = item.querySelector('.checkOne');
            if (!checkbox.checked) return;

            const priceEl = item.querySelector('.itemPrice');

            const qty = parseInt(priceEl.getAttribute('data-quantity') || '0');
            const price = parseInt(priceEl.getAttribute('data-price') || '0');
            const discount = parseInt(priceEl.getAttribute('data-discount') || '0');
            const delivery = parseInt(priceEl.getAttribute('data-delivery') || '0');

            const baseTotal = price * qty;
            const discountAmount = Math.floor(price * (discount / 100)) * qty;

            productTotal += baseTotal;
            discountTotal += discountAmount;
            deliveryTotal += delivery;
        });

        const deliveryCapped = Math.min(deliveryTotal, 3000);
        const finalTotal = productTotal - discountTotal + deliveryCapped;

        document.querySelector('.priceInfoBox .priceInfos:nth-child(2) .f-w-600').textContent = formatNumber(productTotal) + '원';
        document.querySelector('.priceInfoBox .priceInfos:nth-child(3) .f-w-600').textContent = '-' + formatNumber(discountTotal) + '원';
        document.querySelector('.priceInfoBox .priceInfos:nth-child(4) .f-w-600').textContent = formatNumber(deliveryCapped) + '원';
        document.querySelector('.totalPrice p:last-child').textContent = formatNumber(finalTotal) + '원';
        document.querySelector('.orderBtn span').textContent = formatNumber(finalTotal) + '원 주문하기';
    }

    // 체크박스 전체선택/해제
    document.querySelector('.checkAll').addEventListener('change', function () {
        const checked = this.checked;
        document.querySelectorAll('.checkOne').forEach(chk => {
            chk.checked = checked;
        });
        recalculateTotal(); // 반영
    });

    document.querySelectorAll('.checkOne').forEach(chk => {
        chk.addEventListener('change', () => {
            const all = document.querySelectorAll('.checkOne');
            const checked = document.querySelectorAll('.checkOne:checked');
            document.querySelector('.checkAll').checked = all.length === checked.length;
            recalculateTotal(); // 반영
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

        const ids = Array.from(checked).map(chk => Number(chk.dataset.id));

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

    // 선택상품 주문 버튼
    document.querySelector('.orderBtn').addEventListener('click', () => {
        const selected = document.querySelectorAll('.checkOne:checked');
        if (selected.length === 0) {
            alert('주문할 상품을 선택하세요.');
            return;
        }

        const productCodes = [];
        const quantities = [];
        const options = [];
        const cartIds = [];

        selected.forEach(chk => {
            const cartItem = chk.closest('.cartItem');

            const productCode = chk.dataset.code;
            const cartId = chk.dataset.id;
            const quantity = cartItem.querySelector('.count').textContent.trim();
            const option = cartItem.querySelector('.itemOption').textContent.trim();

            productCodes.push(productCode);
            quantities.push(quantity);
            options.push(option);
            cartIds.push(cartId);
        });

        const queryParams = [];

        productCodes.forEach((code, i) => {
            queryParams.push(`productCode=${code}`);
            queryParams.push(`quantity=${quantities[i]}`);
            queryParams.push(`option=${encodeURIComponent(options[i])}`);
        });

        cartIds.forEach(id => {
            queryParams.push(`cartIds=${id}`);
        });

        const query = queryParams.join('&');
        location.href = `/product/payment?${query}`;
    });


    recalculateTotal(); // 페이지 로딩 시 초기 계산
});
