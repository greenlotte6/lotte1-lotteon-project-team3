document.addEventListener('DOMContentLoaded', function () {

    const reName = /^[가-힣]{2,10}$/;

    let isNameOk = false;
    let isEmailOk = false;

    const name = document.querySelector('input[name="name"]').value.trim();
    const email = document.querySelector('input[name="email"]').value.trim();

    //이름 실시간 유효성검사 및 중복체크
    name.addEventListener('input', () => {
        clearTimeout(nameTimeout); //이전 타이머 제거
        nameTimeout = setTimeout(checkName, 300);
    });

    async function checkName() {
        const value = name.value;

        try {
            const response = await fetch(`/member/checkName/${value}`);
            const data = await response.json();

            if (data.exists) {
                isNameOk = false;
            } else {
                isNameOk = true;
            }
        } catch (err) {
            isNameOk = false;
        }
    }

    //이메일 중복체크
    email.addEventListener('input', () => {
        clearTimeout(emailTimeout); //이전 타이머 제거
        emailTimeout = setTimeout(checkEmail, 300);
    });

    async function checkEmail() {
        const value = email.value;

        try {
            const response = await fetch(`/member/checkEmail/${value}`);
            const data = await response.json();

            if (data.exists) {
                isEmailOk = false;
            } else {
                isEmailOk = true;
            }
        } catch (err) {
            isEmailOk = false;
        }
    }


    //회원가입 전송 시 유효성 검사
    document.querySelector('form').addEventListener('submit', function (e) {
        if (!isNameOk || !isEmailOk) {
            e.preventDefault();
            alert('입력값을 확인해주세요.');
        }
    });
});