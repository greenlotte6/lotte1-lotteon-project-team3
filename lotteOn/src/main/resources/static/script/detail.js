document.addEventListener('DOMContentLoaded', () => {
    const selectedContainer = document.querySelector('.selectedOptions');
    const selects = document.querySelectorAll('.option-select');

    const minusBtn = document.querySelector('.minus');
    const plusBtn = document.querySelector('.plus');
    const countSpan = document.querySelector('.totalCount');
    const priceSpan = document.querySelector('.totalPrice');
    const unitPrice = parseInt(priceSpan.dataset.baseprice);

    let count = 1;

    // 옵션 선택 이벤트
    selects.forEach(select => {
        select.addEventListener('change', (e) => {
            const value = e.target.value;
            const name = e.target.dataset.name;

            // 중복 방지
            if (!value || selectedContainer.querySelector(`[data-name="${name}"][data-value="${value}"]`)) return;

            const selected = document.createElement('div');
            selected.classList.add('selectedOption');
            selected.dataset.name = name;
            selected.dataset.value = value;

            selected.innerHTML = `
                <p>${name}: ${value}</p>
                <button class="removeBtn">X</button>
            `;
            selectedContainer.appendChild(selected);

            selected.querySelector('.removeBtn').addEventListener('click', () => {
                selected.remove();
            });

            updateTotal();
        });
    });

    // 수량 조정
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

    // 총 금액, 수량 업데이트
    function updateTotal() {
        countSpan.textContent = count;
        priceSpan.textContent = (unitPrice * count).toLocaleString();
    }

    // 초기 금액 세팅
    updateTotal();
});
