document.addEventListener('DOMContentLoaded', function () {
    setupNameEmailChecker('findIdForm');
    setupIdEmailChecker('findPwForm');
});

function setupNameEmailChecker(formId) {
    const form = document.getElementById(formId);
    const nameInput = form.querySelector('input[name="name"]');
    const emailInput = form.querySelector('input[name="email"]');
    const bringMeCode = form.querySelector('.bringMeCode');

    let timeout;

    nameInput.addEventListener('input', () => {
        clearTimeout(timeout);
        timeout = setTimeout(checkNameAndEmail, 300);
    });

    emailInput.addEventListener('input', () => {
        clearTimeout(timeout);
        timeout = setTimeout(checkNameAndEmail, 300);
    });

    async function checkNameAndEmail() {
        const name = nameInput.value;
        const email = emailInput.value;

        if (!name || !email) {
            return;
        }

        try {
            const response = await fetch("/member/check-name-email", {
                method: "POST",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email })
            });
            const data = await response.json();

            if (data.valid) {
                bringMeCode.disabled = false;
            } else {
                bringMeCode.disabled = true;
            }
        } catch (err) {
            alert('서버 오류가 발생했습니다.');
        }
    }
}

function setupIdEmailChecker(formPass) {
    const form = document.getElementById(formPass);
    const idInput = form.querySelector('input[name="id"]');
    const emailInput = form.querySelector('input[name="email"]');
    const bringMeCode = form.querySelector('.bringMeCode');

    let timeout;

    idInput.addEventListener('input', () => {
        clearTimeout(timeout);
        timeout = setTimeout(checkIdAndEmail, 300);
    });

    emailInput.addEventListener('input', () => {
        clearTimeout(timeout);
        timeout = setTimeout(checkIdAndEmail, 300);
    });

    async function checkIdAndEmail() {
        const id = idInput.value;
        const email = emailInput.value;

        if (!id || !email) {
            return;
        }

        try {
            const response = await fetch("/member/check-id-email", {
                method: "POST",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id, email })
            });
            const data = await response.json();

            if (data.valid) {
                bringMeCode.disabled = false;
            } else {
                bringMeCode.disabled = true;
            }
        } catch (err) {
            alert('서버 오류가 발생했습니다.');
        }
    }
}