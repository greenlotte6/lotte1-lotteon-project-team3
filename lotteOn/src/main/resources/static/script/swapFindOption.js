document.addEventListener('DOMContentLoaded', function () {
    const findIdBtn = document.querySelector(".findIdBtn");
    const findPassBtn = document.querySelector(".findPassBtn");
    const openId = document.querySelector(".openId");
    const openPass = document.querySelector(".openPass");
    const findTitle = document.querySelector(".findTitle");
    const idConfirm = document.querySelector(".idConfirm");
    const passConfirm = document.querySelector(".passConfirm");

    findIdBtn.addEventListener("click", function () {
        openId.classList.remove("disable");
        openPass.classList.add("disable");

        findIdBtn.classList.add("iWant");
        findPassBtn.classList.remove("iWant");

        findTitle.innerText = "아이디 찾기";
    });

    findPassBtn.addEventListener("click", function () {
        openPass.classList.remove("disable");
        openId.classList.add("disable");

        findPassBtn.classList.add("iWant");
        findIdBtn.classList.remove("iWant");

        findTitle.innerText = "비밀번호 찾기";
    });

    idConfirm.addEventListener("click", function () {
        idConfirm.type = "submit";
    });

    passConfirm.addEventListener("click", function () {
        passConfirm.type = "submit";
    });
});
