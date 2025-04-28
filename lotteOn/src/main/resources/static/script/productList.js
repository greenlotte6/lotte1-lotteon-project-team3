document.addEventListener('DOMContentLoaded', () => {

    /* ✅ 등록 모달 열기 */
    setupModal('.regbutton', 'bannerModal', '.modal .close');

    /* ✅ 수정 모달 열기 */
    document.querySelectorAll('.modbutton').forEach(button => {
        button.addEventListener('click', () => {
            const tr = button.closest('tr');
            const productCode = tr.querySelector('td:nth-child(3)').innerText;
            console.log("✅ 수정 productCode:", productCode);

            fetch(`/admin/product/edit/code/${productCode}`)
                .then(res => res.json())
                .then(product => {
                    console.log('✅ 불러온 product:', product);

                    const modal = document.getElementById('subModal');
                    modal.style.display = 'block';

                    document.querySelector('#subModal input[name="id"]').value = product.id;
                    document.querySelector('#subModal input[name="name"]').value = product.name;
                    document.querySelector('#subModal input[name="description"]').value = product.description;
                    document.querySelector('#subModal input[name="companyName"]').value = product.companyName;
                    document.querySelector('#subModal input[name="price"]').value = product.price;
                    document.querySelector('#subModal input[name="discount"]').value = product.discount;
                    document.querySelector('#subModal input[name="point"]').value = product.point;
                    document.querySelector('#subModal input[name="stock"]').value = product.stock;
                    document.querySelector('#subModal input[name="deliveryFee"]').value = product.deliveryFee;

                    if (product.notice) {
                        document.querySelector('#subModal input[name="notice.prodStatus"]').value = product.notice.prodStatus;
                        document.querySelector('#subModal input[name="notice.vatYn"]').value = product.notice.vatYn;
                        document.querySelector('#subModal input[name="notice.receiptYn"]').value = product.notice.receiptYn;
                        document.querySelector('#subModal input[name="notice.businessType"]').value = product.notice.businessType;
                        document.querySelector('#subModal input[name="notice.origin"]').value = product.notice.origin;
                    }

                    // 옵션 세팅
                    const table = document.getElementById('optionsecTable');
                    table.innerHTML = '';

                    if (!product.options || product.options.length === 0) {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <th>옵션1</th>
                            <td><div><input type="text" name="options[0].optionName" placeholder="옵션1 입력"></div></td>
                            <th>옵션1 항목</th>
                            <td><div><input type="text" name="options[0].optionValue" placeholder="옵션1 항목 입력"></div></td>
                        `;
                        table.appendChild(row);
                    } else {
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
                    }

                    // 카테고리 세팅
                    setTimeout(() => {
                        const firstSelect = document.getElementById('category3');
                        const secondSelect = document.getElementById('category4');

                        const allFirstOptions = firstSelect.options;
                        for (let i = 0; i < allFirstOptions.length; i++) {
                            const opt = allFirstOptions[i];
                            const children = JSON.parse(opt.dataset.children || '[]');
                            const findChild = children.find(child => child.categoryId == product.categoryId);

                            if (findChild) {
                                firstSelect.selectedIndex = i;
                                secondSelect.innerHTML = '<option>2차 분류 선택</option>';
                                children.forEach(child => {
                                    const option = document.createElement('option');
                                    option.value = child.categoryId;
                                    option.textContent = child.name;
                                    secondSelect.appendChild(option);
                                });
                                secondSelect.value = product.categoryId;
                                break;
                            }
                        }
                    }, 300);
                });
        });
    });

    /* ✅ 삭제 버튼 */
    document.querySelectorAll('.delete').forEach(button => {
        button.addEventListener('click', () => {
            const tr = button.closest('tr');
            const productCode = tr.querySelector('td:nth-child(3)').innerText;

            if (confirm("정말 삭제하시겠습니까?")) {
                fetch(`/admin/product/delete/${productCode}`, { method: 'DELETE' })
                    .then(res => {
                        if (res.ok) {
                            alert('삭제되었습니다.');
                            tr.remove();
                        } else {
                            alert('삭제 실패.');
                        }
                    })
                    .catch(err => {
                        console.error(err);
                        alert('삭제 중 오류 발생');
                    });
            }
        });
    });

    /* ✅ 체크박스 전체선택 */
    const checkAll = document.querySelector('th input[type="checkbox"]');
    const checkboxes = document.querySelectorAll('td input[type="checkbox"]');

    checkAll.addEventListener('change', () => {
        checkboxes.forEach(cb => cb.checked = checkAll.checked);
    });

    /* ✅ 선택삭제 버튼 */
    document.querySelector('.delbutton').addEventListener('click', () => {
        const selectedRows = [];

        checkboxes.forEach(cb => {
            if (cb.checked) {
                const tr = cb.closest('tr');
                const productCode = tr.querySelector('td:nth-child(3)').innerText;
                selectedRows.push({ tr, productCode });
            }
        });

        if (selectedRows.length === 0) {
            alert("삭제할 상품을 선택해주세요.");
            return;
        }
        if (!confirm("정말 선택한 상품을 삭제하시겠습니까?")) return;

        selectedRows.forEach(({ tr, productCode }) => {
            fetch(`/admin/product/delete/${productCode}`, { method: 'DELETE' })
                .then(res => {
                    if (res.ok) {
                        tr.remove();
                    } else {
                        alert(`상품 ${productCode} 삭제 실패`);
                    }
                })
                .catch(err => {
                    console.error(err);
                    alert(`상품 ${productCode} 삭제 중 오류 발생`);
                });
        });
    });

    /* ✅ 옵션 추가 버튼 */
    const setupOptionAdd = (tableId, btnId) => {
        const table = document.getElementById(tableId);
        const button = document.getElementById(btnId);
        let count = table.querySelectorAll('tr').length;

        button.addEventListener('click', () => {
            const newRow = document.createElement('tr');
            newRow.innerHTML = `
                <th>옵션${count + 1}</th>
                <td><div><input type="text" name="options[${count}].optionName" placeholder="옵션${count + 1} 입력"></div></td>
                <th>옵션${count + 1} 항목</th>
                <td><div><input type="text" name="options[${count}].optionValue" placeholder="옵션${count + 1} 항목 입력"></div></td>
            `;
            table.appendChild(newRow);
            count++;
        });
    };

    setupOptionAdd('optionTable', 'addOptionBtn');
    setupOptionAdd('optionsecTable', 'addsecOptionBtn');

    setupModal('.regbutton', 'bannerModal', '.modal .close');   // 등록 모달
    setupModal('.modbutton', 'subModal', '.submodal .close');   // 수정 모달

    /* ✅ 카테고리 드롭다운 */
    const setupCategoryDropdown = (firstSelector, secondSelector) => {
        const firstSelect = document.querySelector(firstSelector);
        const secondSelect = document.querySelector(secondSelector);

        fetch('/admin/config/category/api')
            .then(res => res.json())
            .then(data => {
                data.forEach(cat => {
                    const option = document.createElement('option');
                    option.value = cat.categoryId;
                    option.textContent = cat.name;
                    option.dataset.children = JSON.stringify(cat.children);
                    firstSelect.appendChild(option);
                });
            });

        firstSelect.addEventListener('change', e => {
            secondSelect.innerHTML = '<option>2차 분류 선택</option>';
            const selectedOption = e.target.selectedOptions[0];
            const children = JSON.parse(selectedOption.dataset.children || '[]');
            children.forEach(child => {
                const option = document.createElement('option');
                option.value = child.categoryId;
                option.textContent = child.name;
                secondSelect.appendChild(option);
            });
        });
    };

    setupCategoryDropdown('#category1', '#category2'); // 등록
    setupCategoryDropdown('#category3', '#category4'); // 수정
});

/* ✅ 모달 열고 닫는 함수 */
function setupModal(openSelector, modalId, closeSelector) {
    document.querySelectorAll(openSelector).forEach(openBtn => {
        const modal = document.getElementById(modalId);
        const closeBtn = modal.querySelector(closeSelector);

        if (!modal || !openBtn || !closeBtn) {
            console.warn(`❗ Modal setup skipped: selector not found`, { openSelector, closeSelector, modalId });
            return;
        }

        openBtn.addEventListener('click', () => {
            modal.style.display = 'block';
        });

        closeBtn.addEventListener('click', () => {
            modal.style.display = 'none';
        });

        window.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
}
