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

    // 열기
    document.querySelectorAll('.modbutton').forEach(button => {
        button.addEventListener('click', function () {
            document.getElementById('subModal').style.display = 'block';

            // 선택된 상품 정보 가져오기
            const tr = button.closest('tr');
            const productCode = tr.querySelector('td:nth-child(3)').innerText;

            fetch(`/admin/product/edit/${id}`)
                .then(res => res.json())
                .then(product => {
                    // 값 세팅
                    document.querySelector('#subModal input[name="name"]').value = product.name;
                    document.querySelector('#subModal input[name="description"]').value = product.description;
                    document.querySelector('#subModal input[name="companyName"]').value = product.companyName;
                    document.querySelector('#subModal input[name="price"]').value = product.price;
                    document.querySelector('#subModal input[name="discount"]').value = product.discount;
                    document.querySelector('#subModal input[name="point"]').value = product.point;
                    document.querySelector('#subModal input[name="stock"]').value = product.stock;
                    document.querySelector('#subModal input[name="deliveryFee"]').value = product.deliveryFee;

                    // 고시 정보
                    if (product.notice) {
                        document.querySelector('#subModal input[name="notice.prodStatus"]').value = product.notice.prodStatus;
                        document.querySelector('#subModal input[name="notice.vatYn"]').value = product.notice.vatYn;
                        document.querySelector('#subModal input[name="notice.receiptYn"]').value = product.notice.receiptYn;
                        document.querySelector('#subModal input[name="notice.businessType"]').value = product.notice.businessType;
                        document.querySelector('#subModal input[name="notice.origin"]').value = product.notice.origin;
                    }

                    // 옵션 초기화 후 추가
                    const table = document.getElementById('optionsecTable');
                    table.innerHTML = '';
                    product.options.forEach((opt, i) => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <th>옵션${i + 1}</th>
                        <td><div><input type="text" name="options[${i}].optionName" value="${opt.optionName}" placeholder="옵션${i + 1} 입력"></div></td>
                        <th>옵션${i + 1} 항목</th>
                        <td><div><input type="text" name="options[${i}].optionValue" value="${opt.optionValue}" placeholder="옵션${i + 1} 항목 입력"></div></td>
                    `;
                        table.appendChild(row);
                    });
                });
        });
    });

    // 닫기
    document.querySelector('.submodal .close').addEventListener('click', function () {
        document.getElementById('subModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function (e) {
        const modal = document.getElementById('subModal');
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


    const thirdSelect = document.querySelector('#category3');
    const fthSelect = document.querySelector('#category4');

    // 카테고리 전체 로딩
    fetch('/admin/config/category/api')
        .then(res => res.json())
        .then(data => {
            data.forEach(cat => {
                const option = document.createElement('option');
                option.value = cat.categoryId;
                option.textContent = cat.name;
                option.dataset.children = JSON.stringify(cat.children); // 2차 저장
                thirdSelect.appendChild(option);
            });
        });

    // 1차 선택 시 → 2차 분류 자동 변경
    thirdSelect.addEventListener('change', e => {
        fthSelect.innerHTML = '<option>2차 분류 선택</option>'; // 초기화

        const selectedOption = e.target.selectedOptions[0];
        console.log("선택된 option:", selectedOption);
        const children = JSON.parse(selectedOption.dataset.children || '[]');
        console.log("자식 목록:", children);

        children.forEach(child => {
            const option = document.createElement('option');
            option.value = child.categoryId;

            option.textContent = child.name;
            fthSelect.appendChild(option);
        });
    });

});