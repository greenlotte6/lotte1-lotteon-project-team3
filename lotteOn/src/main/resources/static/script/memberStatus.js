document.addEventListener('DOMContentLoaded', function () {
    let statusList = document.querySelectorAll(".memberStatus");

    statusList.forEach((status) => {
        if (status.innerText === "정상") {
            status.style.color = "green";
        } else if (status.innerText === "중지") {
            status.style.color = "#E60012";
        } else {
            status.style.color = "gray";
        }
    });
});