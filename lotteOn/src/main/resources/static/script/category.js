document.addEventListener('DOMContentLoaded', () => {
    const categoryList = document.getElementById('categoryList');
    const addCategoryBtn = document.getElementById('addCategoryBtn');
    const saveBtn = document.querySelector('.btnarea button[type="submit"]');

    // ✅ 카테고리 목록 조회
    fetch('/admin/config/category/api')
        .then(res => res.json())
        .then(data => {
            renderCategories(data);
        });

    // ✅ 1차 카테고리 추가
    addCategoryBtn.addEventListener('click', () => {
        const newCategory = createCategoryElement();
        categoryList.appendChild(newCategory);
    });

    // ✅ 저장하기
    saveBtn.addEventListener('click', () => {
        const data = getCategoryData();
        fetch('/admin/config/category/api', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        }).then(() => {
            alert("저장 완료");
            location.reload();
        });
    });
});

function renderCategories(categories) {
    categories.forEach(cat => {
        const catEl = createCategoryElement(cat.name, cat.children);
        document.getElementById('categoryList').appendChild(catEl);
    });
}

function createCategoryElement(name = '', children = []) {
    const wrapper = document.createElement('div');
    wrapper.classList.add('category-item');

    const input = document.createElement('input');
    input.type = 'text';
    input.value = name;
    input.placeholder = '1차 카테고리명';

    const addSubBtn = document.createElement('button');
    addSubBtn.type = 'button';
    addSubBtn.textContent = '+ 2차카테고리 추가';
    addSubBtn.className = 'addbtn'
    addSubBtn.addEventListener('click', () => {
        wrapper.appendChild(createSubCategoryElement());
    });

    wrapper.appendChild(input);
    wrapper.appendChild(addSubBtn);

    children?.forEach(sub => {
        wrapper.appendChild(createSubCategoryElement(sub.name));
    });

    return wrapper;
}

function createSubCategoryElement(name = '') {
    const input = document.createElement('input');
    input.type = 'text';
    input.classList.add('sub-category');
    input.placeholder = '2차 카테고리명';
    input.value = name;
    return input;
}

function getCategoryData() {
    const result = [];
    document.querySelectorAll('.category-item').forEach(item => {
        const inputs = item.querySelectorAll('input');
        const parentName = inputs[0].value.trim();

        if (!parentName) return; // 빈 이름은 제외

        const children = Array.from(inputs)
            .slice(1)
            .filter(input => input.value.trim() !== '')
            .map(input => ({
                name: input.value.trim(),
                depth: 2
            }));

        result.push({
            name: parentName,
            depth: 1,
            children: children
        });
    });
    return result;
}
