document.addEventListener("DOMContentLoaded", function () {
    const shopStates = {
        1: 'active',   // 상점 1은 초기 상태가 '운영중'
        2: 'paused'    // 상점 2는 초기 상태가 '운영중지'
    };

// 상태에 따라 색상을 설정하는 함수
    function updateShopStatus(shopId) {
        const statusDiv = document.getElementById(`status-${shopId}`);
        const button = document.getElementById(`button-${shopId}`);

        if (shopStates[shopId] === 'active') {
            statusDiv.style.backgroundColor = 'green';  // 초록불
            button.textContent = '중단';                  // '중단' 버튼
        } else if (shopStates[shopId] === 'paused') {
            statusDiv.style.backgroundColor = 'red';    // 빨간불
            button.textContent = '재개';                  // '재개' 버튼
        } else if (shopStates[shopId] === 'pending') {
            statusDiv.style.backgroundColor = 'blue';   // 파란불
            button.textContent = '승인';                  // '승인' 버튼
        }
    }

// 각 상점 상태 초기화
    Object.keys(shopStates).forEach(shopId => updateShopStatus(shopId));

// 버튼 클릭 시 상태 변경
    document.querySelectorAll('button').forEach(button => {
        button.addEventListener('click', (event) => {
            const shopId = event.target.id.split('-')[1];  // 버튼 ID에서 상점 ID 추출

            // 상태 변경 로직
            if (shopStates[shopId] === 'active') {
                shopStates[shopId] = 'paused';  // '운영 중' -> '중단'
            } else if (shopStates[shopId] === 'paused') {
                shopStates[shopId] = 'active';  // '중단' -> '운영 중'
            } else if (shopStates[shopId] === 'pending') {
                shopStates[shopId] = 'active';  // '승인 대기' -> '운영 중'
            }

            updateShopStatus(shopId);  // 상태 업데이트
        });
    });
});
