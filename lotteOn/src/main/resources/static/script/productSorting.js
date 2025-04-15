document.addEventListener('DOMContentLoaded', function() {
    const selected = document.querySelectorAll(".sortList > ul li > a");

    selected.forEach(element => {
        element.addEventListener("click", function() {
            selected.forEach(el => el.classList.remove("selected"));
            this.classList.add("selected");
        })
    });
});