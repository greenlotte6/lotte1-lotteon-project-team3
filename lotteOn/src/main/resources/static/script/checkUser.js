document.addEventListener('DOMContentLoaded', function() {
    const checkMember = document.querySelector("#checkMember");
    const checkSeller = document.querySelector("#checkSeller");

    checkMember.addEventListener("change", function() {
        if (checkMember.checked) {
            checkSeller.checked = false;
        }
    });

    checkSeller.addEventListener("change", function() {
        if (checkSeller.checked) {
            checkMember.checked = false;
        }
    });
});