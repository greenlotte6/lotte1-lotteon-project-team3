document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll(".star");
    const ratingInput = document.getElementById("rating");

    stars.forEach(star => {
        star.addEventListener("click", function() {
            const value = this.getAttribute("data-value");
            const reviewForm = this.closest(".review_form"); // 이 별이 속한 리뷰 영역
            const ratingInput = reviewForm.querySelector("input[name='rating']"); // 해당 폼의 rating input

            ratingInput.value = value;

            const allStars = reviewForm.querySelectorAll(".star");
            allStars.forEach(s => {
                if (s.getAttribute("data-value") <= value) {
                    s.classList.add("selected");
                } else {
                    s.classList.remove("selected");
                }
            });
        });

        star.addEventListener("mouseover", function() {
            const value = this.getAttribute("data-value");
            const reviewForm = this.closest(".review_form");
            const allStars = reviewForm.querySelectorAll(".star");

            allStars.forEach(s => {
                if (s.getAttribute("data-value") <= value) {
                    s.classList.add("selected");
                } else {
                    s.classList.remove("selected");
                }
            });
        });

        star.addEventListener("mouseout", function() {
            const reviewForm = this.closest(".review_form");
            const ratingInput = reviewForm.querySelector("input[name='rating']");
            const value = ratingInput.value;
            const allStars = reviewForm.querySelectorAll(".star");

            allStars.forEach(s => {
                s.classList.remove("selected");
                if (s.getAttribute("data-value") <= value) {
                    s.classList.add("selected");
                }
            });
        });
    });
});