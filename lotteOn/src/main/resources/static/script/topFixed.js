window.addEventListener('scroll', function() {
    let fixed = document.querySelector(".fixed");
    let substitution = document.querySelector(".substitution");

    let topPos = fixed.getBoundingClientRect().top;
    let substitutionPos = substitution.getBoundingClientRect().top;

    if (topPos < 0) {
        fixed.classList.add("topFixed");
        fixed.classList.remove("mr-tb");
        substitution.style.display = "block";
    } else if(substitutionPos > 0){
        fixed.classList.add("mr-tb");
        fixed.classList.remove("topFixed");
        substitution.style.display = "none";
    }
});
