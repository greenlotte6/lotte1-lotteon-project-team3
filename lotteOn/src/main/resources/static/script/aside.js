document.addEventListener('DOMContentLoaded', function () {
    const items = document.querySelectorAll(".item");

    items.forEach(item => {
        const title = item.querySelector("div:first-child");
        const dropdown = item.querySelector(".dropdown");

        title.addEventListener("click", () => {
            const isOpen = dropdown.classList.contains("open");

            document.querySelectorAll(".dropdown").forEach(d => d.classList.remove("open"));

            if(!isOpen) {
                dropdown.classList.add("open");
            }
        });
    });
});
