document.addEventListener('DOMContentLoaded', function () {

    const periodSelect = document.getElementById('period');
    const selectDate = document.querySelector('.selectDate');
    const searchButton = document.querySelector('.searchButton');

    function toggleDateInputs() {
        const isCustom = periodSelect.value === 'custom';
        selectDate.style.display = isCustom ? 'inline-block' : 'none';
        searchButton.style.marginLeft = isCustom ? "8px" : "0px";
    }

    periodSelect.addEventListener('change', toggleDateInputs);
    window.addEventListener('DOMContentLoaded', toggleDateInputs);
});
