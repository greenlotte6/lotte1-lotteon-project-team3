document.addEventListener('DOMContentLoaded', () => {

    // 열기
    document.querySelector('.regbutton').addEventListener('click', function () {
        document.getElementById('bannerModal').style.display = 'block';
    });

    // 닫기
    document.querySelector('.modal .close').addEventListener('click', function () {
        document.getElementById('bannerModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function (e) {
        const modal = document.getElementById('bannerModal');
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    const firstSelect = document.querySelector('#category1');
    const secondSelect = document.querySelector('#category2');

    // 카테고리 전체 로딩
    fetch('/admin/config/category/api')
        .then(res => res.json())
        .then(data => {
            data.forEach(cat => {
                const option = document.createElement('option');
                option.value = cat.categoryId;
                option.textContent = cat.name;
                option.dataset.children = JSON.stringify(cat.children); // 2차 저장
                firstSelect.appendChild(option);
            });
        });

    // 1차 선택 시 → 2차 분류 자동 변경
    firstSelect.addEventListener('change', e => {
        secondSelect.innerHTML = '<option>2차 분류 선택</option>'; // 초기화

        const selectedOption = e.target.selectedOptions[0];
        console.log("선택된 option:", selectedOption);
        const children = JSON.parse(selectedOption.dataset.children || '[]');
        console.log("자식 목록:", children);

        children.forEach(child => {
            const option = document.createElement('option');
            option.value = child.categoryId;

            option.textContent = child.name;
            secondSelect.appendChild(option);
        });
    });
});