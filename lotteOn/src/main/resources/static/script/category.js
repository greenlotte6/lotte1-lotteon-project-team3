document.addEventListener('DOMContentLoaded', () => {
    const categoryList = document.getElementById('categoryList');
    const addCategoryBtn = document.getElementById('addCategoryBtn');

    // ✅ 2차 카테고리 생성 함수
    function createSubcategory(name, container) {
        const subItem = document.createElement('div');
        subItem.className = 'subcategory-item';
        subItem.innerHTML = `
                ${name} <button class="delete-btn">삭제</button>
            `;
        subItem.querySelector('.delete-btn').addEventListener('click', () => subItem.remove());
        container.appendChild(subItem);
    }

    // ✅ 1차 카테고리 생성 함수
    function createCategory(name) {
        const categoryItem = document.createElement('div');
        categoryItem.className = 'category-item';

        categoryItem.innerHTML = `
                <div class="category-header">
                    <button class="toggle">▶</button> ${name}
                    <button class="delete-btn">삭제</button>
                </div>
                <div class="subcategory" style="display: none;"></div>
                <button class="add-subcategory" style="display: none;">+ 2차카테고리추가</button>
            `;

        const toggleBtn = categoryItem.querySelector('.toggle');
        const subContainer = categoryItem.querySelector('.subcategory');
        const addSubBtn = categoryItem.querySelector('.add-subcategory');

        // 아코디언 토글 + 2차 버튼 제어
        toggleBtn.addEventListener('click', () => {
            const isOpen = subContainer.style.display === 'block';
            subContainer.style.display = isOpen ? 'none' : 'block';
            addSubBtn.style.display = isOpen ? 'none' : 'inline-block';
            toggleBtn.textContent = isOpen ? '▶' : '▼';
        });

        // 1차 삭제
        categoryItem.querySelector('.category-header .delete-btn').addEventListener('click', () => {
            categoryItem.remove();
        });

        // 2차 카테고리 추가
        addSubBtn.addEventListener('click', () => {
            const subName = prompt('2차 카테고리명을 입력하세요');
            if (subName) createSubcategory(subName, subContainer);
        });

        categoryList.appendChild(categoryItem);
    }

    // ✅ 1차 카테고리 추가 버튼
    addCategoryBtn.addEventListener('click', () => {
        const catName = prompt('1차 카테고리명을 입력하세요');
        if (catName) createCategory(catName);
    });
});