
    const sections = document.querySelectorAll('.notice_block');

    sections.forEach(section => {
    const table = section.querySelector('.noticeTable');
    const rows = table.querySelectorAll('tr');
    const btn = section.querySelector('.toggleBtn');

    rows.forEach((row, index) => {
    row.style.display = index < 3 ? '' : 'none';
});

    let expanded = false;

    btn.addEventListener('click', () => {
    expanded = !expanded;
    rows.forEach((row, index) => {
    row.style.display = expanded ? '' : (index < 3 ? '' : 'none');
});
    btn.textContent = expanded ? '간단히 보기▲' : '더보기▼';
});
});
