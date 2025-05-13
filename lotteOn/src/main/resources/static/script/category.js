/*document.addEventListener('DOMContentLoaded', () => {
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
        const catEl = createCategoryElement(cat.name, cat.children, cat.categoryId);
        document.getElementById('categoryList').appendChild(catEl);
    });
}

function createCategoryElement(name = '', children = [], categoryId = null) {
    const wrapper = document.createElement('div');
    wrapper.classList.add('category-item');

    const input = document.createElement('input');
    input.type = 'text';
    input.value = name;
    input.placeholder = '1차 카테고리명';
    if (categoryId) input.dataset.id = categoryId;

    const addSubBtn = document.createElement('button');
    addSubBtn.type = 'button';
    addSubBtn.textContent = '+ 2차카테고리 추가';
    addSubBtn.className = 'addbtn';
    addSubBtn.addEventListener('click', () => {
        wrapper.appendChild(createSubCategoryElement());
    });

    wrapper.appendChild(input);
    wrapper.appendChild(addSubBtn);

    children?.forEach(sub => {
        wrapper.appendChild(createSubCategoryElement(sub.name, sub.categoryId));
    });

    return wrapper;
}

function createSubCategoryElement(name = '', categoryId = null) {
    const input = document.createElement('input');
    input.type = 'text';
    input.classList.add('sub-category');
    input.placeholder = '2차 카테고리명';
    input.value = name;
    if (categoryId) input.dataset.id = categoryId;
    return input;
}


function getCategoryData() {
    const result = [];
    document.querySelectorAll('.category-item').forEach(item => {
        const inputs = item.querySelectorAll('input');
        const parentInput = inputs[0];
        const parentName = parentInput.value.trim();
        const parentId = parentInput.dataset.id;

        if (!parentName) return;

        const children = Array.from(inputs)
            .slice(1)
            .filter(input => input.value.trim() !== '')
            .map(input => ({
                categoryId: input.dataset.id || null,
                name: input.value.trim(),
                depth: 2
            }));

        result.push({
            categoryId: parentId || null,
            name: parentName,
            depth: 1,
            children: children
        });
    });
    return result;
}
 */
document.addEventListener('DOMContentLoaded', () => {
    const categoryList = document.getElementById('categoryList');
    const addCategoryBtn = document.getElementById('addCategoryBtn');
    const saveBtn = document.querySelector('.btnarea button[type="submit"]');

    fetch('/admin/config/category/api')
        .then(res => res.json())
        .then(data => renderCategories(data));

    addCategoryBtn.addEventListener('click', () => {
        const newCategory = createCategoryElement();
        categoryList.appendChild(newCategory);
        makeDraggable();
    });

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

    function renderCategories(categories) {
        categories.forEach(cat => {
            const catEl = createCategoryElement(cat.name, cat.children, cat.categoryId);
            categoryList.appendChild(catEl);
        });
        makeDraggable();
    }

    function createCategoryElement(name = '', children = [], categoryId = null) {
        const wrapper = document.createElement('div');
        wrapper.classList.add('category-item');
        wrapper.setAttribute('draggable', 'true');

        const input = document.createElement('input');
        input.type = 'text';
        input.value = name;
        input.placeholder = '1차 카테고리명';
        if (categoryId) input.dataset.id = categoryId;

        const addSubBtn = document.createElement('button');
        addSubBtn.type = 'button';
        addSubBtn.textContent = '+ 2차카테고리 추가';
        addSubBtn.className = 'addbtn';
        addSubBtn.addEventListener('click', () => {
            wrapper.appendChild(createSubCategoryElement());
        });

        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.textContent = '삭제';
        deleteBtn.className = 'deletebtn';
        deleteBtn.addEventListener('click', () => wrapper.remove());

        wrapper.appendChild(input);
        wrapper.appendChild(addSubBtn);
        wrapper.appendChild(deleteBtn);

        children?.forEach(sub => {
            wrapper.appendChild(createSubCategoryElement(sub.name, sub.categoryId));
        });

        return wrapper;
    }

    function createSubCategoryElement(name = '', categoryId = null) {
        const container = document.createElement('div');
        container.classList.add('sub-category-wrapper');

        const input = document.createElement('input');
        input.type = 'text';
        input.classList.add('sub-category');
        input.placeholder = '2차 카테고리명';
        input.value = name;
        if (categoryId) input.dataset.id = categoryId;

        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.textContent = '삭제';
        deleteBtn.className = 'deletebtn';
        deleteBtn.addEventListener('click', () => container.remove());

        container.appendChild(input);
        container.appendChild(deleteBtn);
        return container;
    }

    function getCategoryData() {
        const result = [];
        document.querySelectorAll('.category-item').forEach((item, index) => {
            const inputs = item.querySelectorAll('input');
            const parentInput = inputs[0];
            const parentName = parentInput.value.trim();
            const parentId = parentInput.dataset.id;

            if (!parentName) return;

            const children = Array.from(item.querySelectorAll('.sub-category-wrapper')).map((wrapper, i) => {
                const input = wrapper.querySelector('input');
                return {
                    categoryId: input.dataset.id || null,
                    name: input.value.trim(),
                    depth: 2,
                    sortOrder: i + 1
                };
            });

            result.push({
                categoryId: parentId || null,
                name: parentName,
                depth: 1,
                sortOrder: index + 1,
                children: children
            });
        });
        return result;
    }

    function makeDraggable() {
        const items = document.querySelectorAll('.category-item');
        let dragged = null;

        items.forEach(item => {
            item.addEventListener('dragstart', () => {
                dragged = item;
            });

            item.addEventListener('dragover', (e) => {
                e.preventDefault();
            });

            item.addEventListener('drop', (e) => {
                e.preventDefault();
                if (dragged && dragged !== item) {
                    categoryList.insertBefore(dragged, item.nextSibling);
                }
            });
        });
    }
});