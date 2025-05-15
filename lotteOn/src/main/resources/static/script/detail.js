document.addEventListener('DOMContentLoaded', () => {
    const selectedContainer = document.querySelector('.selectedOptions');
    const selects = document.querySelectorAll('.option-select');
    const quantityInput = document.querySelector("#inputQuantity");
    const minusBtn = document.querySelector('.minus');
    const plusBtn = document.querySelector('.plus');
    const countSpan = document.querySelector('.totalCount');
    const priceSpan = document.querySelector('.totalPrice');
    const unitPrice = parseInt(priceSpan.dataset.baseprice);

    const addCartBtn = document.querySelector('.addCart');
    const productCode = document.querySelector('.no').textContent.trim();
    const isLogin = document.body.dataset.login === 'true'; // body에 data-login 속성 필요

    let count = 1;

    // 옵션 선택
    selects.forEach(select => {
        select.addEventListener('change', (e) => {
            const value = e.target.value;
            const name = e.target.dataset.name;

            if (!value || selectedContainer.querySelector(`[data-name="${name}"][data-value="${value}"]`)) return;

            const selected = document.createElement('div');
            selected.classList.add('selectedOption');
            selected.dataset.name = name;
            selected.dataset.value = value;

            selected.innerHTML = `
                <p>${name}: ${value}</p>
                <button class="removeBtn"></button>
            `;
            selectedContainer.appendChild(selected);

            selected.querySelector('.removeBtn').addEventListener('click', () => {
                selected.remove();
            });

            updateTotal();
        });
    });
    document.getElementById('cartForm').addEventListener('submit', function (e) {
        const selects = document.querySelectorAll('.option-select');
        let optionSummary = [];

        for (let select of selects) {
            const label = select.dataset.name;
            const value = select.value;

            if (!value) {
                alert(label + ' 옵션을 선택하세요.');
                e.preventDefault();
                return;
            }

            optionSummary.push(label + ':' + value);
        }

        document.getElementById('selectedOption').value = optionSummary.join(', ');
    });

    // 수량 조절
    plusBtn.addEventListener('click', () => {
        count++;
        updateTotal();
    });

    minusBtn.addEventListener('click', () => {
        if (count > 1) {
            count--;
            updateTotal();
        }
    });

    function updateTotal() {
        countSpan.textContent = count;
        priceSpan.textContent = (unitPrice * count).toLocaleString();
    }

    // 장바구니 담기
    addCartBtn.addEventListener('click', () => {
        if (!isLogin) {
            location.href = '/member/login';
            return;
        }

        fetch('/product/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ productCode, count })
        })
            .then(res => {
                if (res.ok) {
                    location.href = '/product/cart';
                } else {
                    alert('장바구니 담기에 실패했습니다.');
                }
            });
    });

    // 초기 총액 세팅
    updateTotal();
    document.getElementById('orderForm').addEventListener('submit', function (e) {
        const selects = document.querySelectorAll('.option-select');
        let optionSummary = [];

        for (let select of selects) {
            const label = select.dataset.name;
            const value = select.value;

            if (!value) {
                alert(label + ' 옵션을 선택하세요.');
                e.preventDefault();
                return;
            }

            optionSummary.push(`${label}:${value}`);
        }

        document.getElementById('optionInput').value = optionSummary.join(', ');
        document.getElementById('inputQuantity').value = count;
    });
});
