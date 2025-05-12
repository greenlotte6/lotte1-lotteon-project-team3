document.addEventListener('DOMContentLoaded', () => {
    const chooseBtn = document.querySelector('.chooseBtn');
    const optionsBox = document.querySelector('.chooseOption .options');
    const optionsList = document.querySelector('#couponList');
    const issuedNoInput = document.querySelector('#issuedNo');
    const usePointBtn = document.querySelector('.usePointBtn');
    const pointInput = document.querySelector('.point');

    const remainPointText = document.querySelector('.remainPoint p');
    const originalTotal = parseInt(document.querySelector('#originalTotal').value); // 원가 총액
    const baseDiscount = parseInt(document.querySelector('.discountAmount').textContent.replace(/[^0-9]/g, '')); // 상품 자체 할인

    let currentCouponDiscount = 0;
    let currentPointDiscount = 0;

    // ✅ 드롭다운 열기/닫기
    chooseBtn.addEventListener('click', () => {
        optionsBox.classList.toggle('open');
    });

    // ✅ 쿠폰 불러오기
    fetch('/product/payment/coupons')
        .then(res => res.json())
        .then(coupons => {
            optionsList.innerHTML = '';

            if (!coupons || coupons.length === 0) {
                optionsList.innerHTML = '<li><p class="couponItem" style="color: gray;">사용 가능한 쿠폰 없음</p></li>';
                return;
            }

            coupons.forEach(coupon => {
                const li = document.createElement('li');
                li.innerHTML = `<p class="couponItem" data-issuedno="${coupon.issuedNo}">
                    ${coupon.couponName} - ${coupon.benefit}
                </p>`;
                optionsList.appendChild(li);
            });
        });

    // ✅ 쿠폰 선택 시
    optionsList.addEventListener('click', (e) => {
        if (!e.target.classList.contains('couponItem')) return;

        chooseBtn.textContent = e.target.textContent;
        issuedNoInput.value = e.target.dataset.issuedno;
        optionsBox.classList.remove('open');

        const benefitText = e.target.textContent.split('-')[1].trim();
        if (benefitText.includes('%')) {
            const rate = parseInt(benefitText.replace('%', ''));
            currentCouponDiscount = Math.floor(originalTotal * rate / 100);
        } else {
            currentCouponDiscount = parseInt(benefitText.replace(/[^0-9]/g, ''));
        }

        updateTotals();
    });

    // ✅ 포인트 사용
    usePointBtn.addEventListener('click', () => {
        const pointValue = parseInt(pointInput.value || '0');
        const remainPoint = parseInt(remainPointText?.textContent.replace(/[^0-9]/g, '') || '0');

        if (pointValue > remainPoint) {
            alert('보유 포인트보다 많이 사용할 수 없습니다.');
            return;
        }
        currentPointDiscount = pointValue;

        document.querySelector('#usedPoint').value = pointValue;

        const newRemain = remainPoint - pointValue;
        remainPointText.textContent = `보유 포인트: ${newRemain.toLocaleString()}P`;

        updateTotals();
    });

    // ✅ 최종 결제 금액 갱신
    function updateTotals() {
        const totalDiscount = baseDiscount + currentCouponDiscount + currentPointDiscount;
        const discountedTotal = originalTotal - baseDiscount - currentCouponDiscount - currentPointDiscount;
        const deliveryFee = parseInt(document.querySelector('.deliveryFee').getAttribute('data-fee'));
        const finalTotal = discountedTotal + deliveryFee;

        document.querySelector('.discountAmount').textContent = totalDiscount.toLocaleString();
        document.querySelector('.deliveryFee').textContent = deliveryFee.toLocaleString();
        document.querySelector('.themeColor').textContent = finalTotal.toLocaleString() + '원';

        // form 전송용 input에 실제 값 반영
        document.querySelector('input[name="discount"]').value = totalDiscount;
        document.querySelector('input[name="actualMoney"]').value = finalTotal;

        // ✅ 여기 추가: 총 건 옆 금액도 업데이트
        const totalPriceElement = document.querySelector('.totalPrice .themeColor');
        if (totalPriceElement) {
            totalPriceElement.textContent = finalTotal.toLocaleString() + '원';
        }


    }
});
