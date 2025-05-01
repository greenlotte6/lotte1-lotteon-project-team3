document.addEventListener('DOMContentLoaded', function () {
    const modalControl = document.getElementById("modalControl");

    document.querySelector(".closeBtn").addEventListener("click", function() {
        document.querySelector("#modalControl textarea").scrollTop = 0;
        modalControl.style.display = "none";
        document.body.style.overflow = "auto";
    });

    document.querySelectorAll(".terms-link").forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            const type = this.dataset.type;

            fetch(`/terms/api?type=${type}`)
                .then(res => res.text())
                .then(data => {
                    document.querySelector("#modalControl .termsTitle").textContent = this.textContent;
                    document.querySelector("#modalControl textarea").value = data;
                    document.getElementById("modalControl").style.display = "block";
                    document.body.style.overflow = "hidden";
                });
        });
    });

});