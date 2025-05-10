document.addEventListener('DOMContentLoaded', function () {
    const myPoint = document.querySelector(".thisIsMyPoint");
    const rawValue = myPoint.innerText.replace(/,/g, '');
    const number = Number(rawValue);
    if (!isNaN(number)) {
        const formatted = number.toLocaleString();
        myPoint.innerText = formatted;
    }
});