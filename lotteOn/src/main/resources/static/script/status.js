document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".manageBtn");

    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            const statusDiv = button.closest("tr").querySelector(".status-circle");
            if (!statusDiv) return;

            let currentStatus = parseInt(statusDiv.dataset.status);
            let nextStatus = (currentStatus + 1) % 3; // 0 → 1 → 2 → 0 순환

            updateStatusUI(statusDiv, button, nextStatus);
        });
    });
});

function updateStatusUI(statusDiv, button, status) {
    statusDiv.dataset.status = status;

    if (status === 0) {
        statusDiv.style.backgroundColor = "blue";
        button.textContent = "[ 승인 ]";
    } else if (status === 1) {
        statusDiv.style.backgroundColor = "green";
        button.textContent = "[ 중단 ]";
    } else if (status === 2) {
        statusDiv.style.backgroundColor = "red";
        button.textContent = "[ 재개 ]";
    }
}
