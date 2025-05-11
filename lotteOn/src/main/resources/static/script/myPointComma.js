document.addEventListener('DOMContentLoaded', function () {
    const myPoints = document.querySelectorAll(".thisIsMyPoint");
    const savePoints = document.querySelectorAll(".savePoints");

    myPoints.forEach(function (point) {
        const rawValue = point.innerText.replace(/,/g, '');
        const number = Number(rawValue);
        if (!isNaN(number)) {
            const formatted = number.toLocaleString();
            point.innerText = formatted;
        }
    });

    savePoints.forEach(function (savePoint) {
        savePoint.innerText += "P";
    });
});