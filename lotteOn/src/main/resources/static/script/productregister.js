document.addEventListener('DOMContentLoaded', () => {
    const optionTable = document.getElementById('optionTable');
    const addBtn = document.getElementById('addOptionBtn');
    let optionCount = 1; // 0번은 이미 있으니까 1부터 시작

    addBtn.addEventListener('click', () => {
        optionCount++;

        const newRow = document.createElement('tr');
        newRow.innerHTML = `
                                <th>옵션${optionCount}</th>
                                <td>
                                    <div>
                                        <input type="text" name="options[${optionCount}].optionName" placeholder="옵션${optionCount} 입력">
                                    </div>
                                </td>
                                <th>옵션${optionCount} 항목</th>
                                <td>
                                    <div>
                                        <input type="text" name="options[${optionCount}].optionValue" placeholder="옵션${optionCount} 항목 입력">
                                    </div>
                                </td>
                            `;
        optionTable.appendChild(newRow);
    });
    const optionsecTable = document.getElementById('optionsecTable');
    const addsecBtn = document.getElementById('addsecOptionBtn');
    let optionsecCount = 1; // 0번은 이미 있으니까 1부터 시작

    addsecBtn.addEventListener('click', () => {
        optionsecCount++;

        const newRow = document.createElement('tr');
        newRow.innerHTML = `
                                <th>옵션${optionsecCount}</th>
                                <td>
                                    <div>
                                        <input type="text" name="options[${optionsecCount}].optionName" placeholder="옵션${optionsecCount} 입력">
                                    </div>
                                </td>
                                <th>옵션${optionsecCount} 항목</th>
                                <td>
                                    <div>
                                        <input type="text" name="options[${optionsecCount}].optionValue" placeholder="옵션${optionsecCount} 항목 입력">
                                    </div>
                                </td>
                            `;
        optionsecTable.appendChild(newRow);
    });
});