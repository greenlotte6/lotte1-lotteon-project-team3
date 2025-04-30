document.addEventListener('DOMContentLoaded', function () {
    const pwd = document.querySelector("input[name='password']");
    const cpwd = document.querySelector("input[name='confirmPassword']");
    const pwdMsg = document.querySelector(".pwdMsg");
    const pwdMsg2 = document.querySelector(".pwdMsg2");
    const resultId = document.querySelector(".resultId");
    const modifyForm = document.getElementById("modifyPass");
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;

    // 실시간으로 비밀번호 확인
    pwd.addEventListener('input', validatePasswords);
    cpwd.addEventListener('input', validatePasswords);

    function validatePasswords() {
        const pwdValue = pwd.value;
        const cpwdValue = cpwd.value;

        // 초기화
        pwdMsg.innerText = '';
        pwdMsg2.innerText = '';

        let formIsValid = true;

        if (!rePass.test(pwdValue)) {
            pwdMsg.innerText = '유효하지 않은 비밀번호입니다.';
            pwdMsg.style.color = 'rgba(230,0,18,0.90)';
            formIsValid = false;
        }

        if (pwdValue !== cpwdValue) {
            pwdMsg2.innerText = '비밀번호가 일치하지 않습니다';
            pwdMsg2.style.color = 'rgba(230,0,18,0.90)';
            formIsValid = false;
        }

        // 폼 제출 방지
        if (!formIsValid || resultId.value === '') {
            modifyForm.querySelector('button[type="submit"]').disabled = true;  // 변경 버튼 비활성화
        } else {
            modifyForm.querySelector('button[type="submit"]').disabled = false;  // 변경 버튼 활성화
        }
    }
});
