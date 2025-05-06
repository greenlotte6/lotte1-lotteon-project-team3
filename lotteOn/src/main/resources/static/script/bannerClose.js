document.addEventListener('DOMContentLoaded', function () {
    const banner = document.getElementById("mainTopBanner");
    const closeBtn = banner.querySelector(".bannerClose");

    closeBtn.addEventListener('click', function () {
        banner.classList.add("hide-banner");
        closeBtn.style.display = "none";

        setTimeout(() => {
            banner.style.display = "none";
        }, 600);
    });
});