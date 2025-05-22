document.addEventListener('DOMContentLoaded', function () {
    const siteBtn = document.querySelector(".site button");
    const logoBtn = document.querySelector(".logo button");
    const corporateInfoBtn = document.querySelector(".corporateInfo button");
    const customerServiceBtn = document.querySelector(".customerService button");
    const copyRightBtn = document.querySelector(".copyRight button");
    const siteForm = document.querySelector(".site form");
    const logoForm = document.querySelector(".logo form");
    const corporateInfoForm = document.querySelector(".corporateInfo form");
    const customerServiceForm = document.querySelector(".customerService form");
    const copyRightForm = document.querySelector(".copyRight form");

    siteBtn.addEventListener('click', async function () {
        if (!confirm("수정하시겠습니까?")) return;

        const formData = new FormData(siteForm);
        console.log(siteForm.action);

        try {
            const response = await fetch(siteForm.action, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("수정했습니다.");
                console.log(siteForm.action);
            } else {
                alert("수정에 실패했습니다. 다시 시도해주세요.");
                console.log(siteForm.action);
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("서버 오류가 발생했습니다.");
            console.log(siteForm.action);
        }
    });

    logoBtn.addEventListener('click', async function () {
        if (!confirm("수정하시겠습니까?")) return;

        const formData = new FormData(logoForm);

        try {
            const response = await fetch(logoForm.action, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("수정했습니다.");
            } else {
                alert("수정에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("서버 오류가 발생했습니다.");
        }
    });

    corporateInfoBtn.addEventListener('click', async function () {
        if (!confirm("수정하시겠습니까?")) return;

        const formData = new FormData(corporateInfoForm);

        try {
            const response = await fetch(corporateInfoForm.action, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("수정했습니다.");
            } else {
                alert("수정에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("서버 오류가 발생했습니다.");
        }
    });

    customerServiceBtn.addEventListener('click', async function () {
        if (!confirm("수정하시겠습니까?")) return;

        const formData = new FormData(customerServiceForm);

        try {
            const response = await fetch(customerServiceForm.action, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("수정했습니다.");
            } else {
                alert("수정에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("서버 오류가 발생했습니다.");
        }
    });

    copyRightBtn.addEventListener('click', async function () {
        if (!confirm("수정하시겠습니까?")) return;

        const formData = new FormData(copyRightForm);

        try {
            const response = await fetch(copyRightForm.action, {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                alert("수정했습니다.");
            } else {
                alert("수정에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (error) {
            console.error("에러 발생:", error);
            alert("서버 오류가 발생했습니다.");
        }
    });

});