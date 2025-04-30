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
                countDiv.textContent = data.updatedQuantity;

                let price = priceEl.dataset.price;
                if (price) {
                    price = price.replace(/,/g, '');
                    const unitPrice = parseInt(price);
                    const totalPrice = unitPrice * data.updatedQuantity;
                    priceEl.textContent = formatNumber(totalPrice) + '원';
                } else {
                    console.warn('data-price 속성 누락!');
                }

                recalculateTotal();
            });
    }

    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }

    function recalculateTotal() {
        let total = 0;
        document.querySelectorAll('.itemPrice').forEach(el => {
            let unit = el.dataset.price;
            if (unit) {
                unit = unit.replace(/,/g, '');
                const price = parseInt(unit);
                const cartItem = el.closest('.cartItem');
                const quantity = parseInt(cartItem.querySelector('.count').textContent);
                total += price * quantity;
            }
        });
        document.querySelector('.totalPrice p:last-child').textContent = formatNumber(total + 3000) + '원';
    }
});
