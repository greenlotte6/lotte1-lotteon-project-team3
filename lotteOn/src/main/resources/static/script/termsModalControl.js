document.addEventListener('DOMContentLoaded', function () {
    const modalControl = document.getElementById("modalControl");

    document.getElementById("openModal").addEventListener("click", function(e) {
        e.preventDefault();
        modalControl.style.display = "block";
        document.body.style.overflow = "hidden";
    });

    document.querySelector(".closeBtn").addEventListener("click", function() {
        modalControl.style.display = "none";
        document.body.style.overflow = "auto";
    });

});