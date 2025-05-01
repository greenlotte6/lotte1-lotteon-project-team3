document.addEventListener('DOMContentLoaded', function () {

    const reUid = /^[a-z]+[a-z0-9]{3,11}$/;
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;
    const reEmail = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    let isIdOk = false;
    let isPwOk = false;
    let isEmailOk = false;
    let isHpOk = false;
    let isJuminOk = false;

    const id = document.getElementById('id');
    const password = document.getElementById('password');
    const password2 = document.getElementById('password2');
    const email = document.getElementById('email');
    const hp = document.getElementById('hp');
    const rrnFrontInput = document.getElementById('rrnFront');
    const rrnBackInput = document.getElementById('rrnBack');

    const idMsg = document.querySelector('.idMsg');
    const pwMsg = document.querySelector('.pwMsg');
    const pw2Msg = document.querySelector('.pw2Msg');
    const emailMsg = document.querySelector('.emailMsg');
    const hpMsg = document.querySelector('.hpMsg');
    const OX = document.getElementById('OX');

    let idTimeout = null;
    let emailTimeout = null;
    let hpTimeout = null;

    id.addEventListener('input', () => {
        clearTimeout(idTimeout);
        idTimeout = setTimeout(checkId, 300);
    });

    async function checkId() {
        const value = id.value;

        if (!reUid.test(value)) {
            idMsg.innerText = '아이디는 영문으로 시작하는 4~12자여야 합니다';
            idMsg.style.color = '#E60012';
            isIdOk = false;
            return;
        }

        try {
            const response = await fetch(`/member/check-member-id/${value}`);
            const data = await response.json();

            if (data.exists) {
                idMsg.innerText = '이미 사용중인 아이디입니다';
                idMsg.style.color = '#E60012';
                isIdOk = false;
            } else {
                idMsg.innerText = '';
                isIdOk = true;
            }
        } catch (err) {
            idMsg.innerText = '서버 오류';
            idMsg.style.color = '#E60012';
            isIdOk = false;
        }
    }

    password.addEventListener('keyup', () => {
        const pw1 = password.value;

        if (!rePass.test(pw1)) {
            pwMsg.innerText = '비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다';
            pwMsg.style.color = '#E60012';
            isPwOk = false;
            return;
        } else {
            pwMsg.innerText = '';
        }

    });

    password2.addEventListener('keyup', () => {
        const pw1 = password.value;
        const pw2 = password2.value;

        if (pw1 !== pw2) {
            pw2Msg.innerText = '비밀번호가 일치하지 않습니다';
            pw2Msg.style.color = '#E60012';
            isPwOk = false;
        } else {
            pw2Msg.innerText = '';
            isPwOk = true;
        }
    });

    email.addEventListener('input', () => {
        clearTimeout(emailTimeout);
        emailTimeout = setTimeout(checkEmail, 300);
    });

    async function checkEmail() {
        const value = email.value;
        const sendEmailBtn = document.getElementById("sendEmailBtn");

        if (!reEmail.test(value)) {
            emailMsg.innerText = '이메일 형식에 맞지 않습니다';
            emailMsg.style.color = '#E60012';
            sendEmailBtn.disabled = true;
            sendEmailBtn.style.cursor = 'not-allowed';
            isEmailOk = false;
            return;
        }

        try {
            const response = await fetch(`/member/checkEmail/${value}`);
            const data = await response.json();

            if (data.exists) {
                emailMsg.innerText = '이미 사용중인 이메일입니다';
                emailMsg.style.color = '#E60012';
                sendEmailBtn.disabled = true;
                sendEmailBtn.style.cursor = 'not-allowed';
                isEmailOk = false;
            } else {
                emailMsg.innerText = '';
                sendEmailBtn.disabled = false;
                sendEmailBtn.style.cursor = 'pointer';
                isEmailOk = true;
            }
        } catch (err) {
            emailMsg.innerText = '서버 오류';
            emailMsg.style.color = '#E60012';
            sendEmailBtn.style.cursor = 'not-allowed';
            isEmailOk = false;
        }
    }

    hp.addEventListener('input', () => {
        clearTimeout(hpTimeout);
        hpTimeout = setTimeout(checkHp, 300);
    });

    async function checkHp() {
        const value = hp.value;

        try {
            const response = await fetch(`/member/checkHp/${value}`);
            const data = await response.json();

            if (data.exists) {
                hpMsg.innerText = '이미 사용중인 휴대폰 번호입니다';
                hpMsg.style.color = '#E60012';
                isHpOk = false;
            } else {
                hpMsg.innerText = '';
                isHpOk = true;
            }
        } catch (err) {
            hpMsg.innerText = '서버 오류';
            hpMsg.style.color = '#E60012';
            isHpOk = false;
        }
    }

    hp.addEventListener('input', function(e) {
        let number = e.target.value.replace(/[^0-9]/g, ''); // 숫자만 남기기

        let phone = '';
        if (number.length < 4) {
            phone = number;
        } else if (number.length < 8) {
            phone = number.slice(0, 3) + '-' + number.slice(3);
        } else {
            phone = number.slice(0, 3) + '-' + number.slice(3, 7) + '-' + number.slice(7, 11);
        }
        e.target.value = phone;
    });

    rrnFrontInput.addEventListener('input', function () {
        let value = this.value;
        if (value.length > 6) {
            this.value = value.slice(0, 6);
        }
        validateRRN(rrnFrontInput.value, rrnBackInput.value);
    });

    rrnBackInput.addEventListener('input', function () {
        let value = this.value;
        if (value.length > 1) {
            this.value = value.slice(0, 1);
        }
        validateRRN(rrnFrontInput.value, rrnBackInput.value);
    });

    function validateRRN(rrnFront, rrnBack) {
        const rrnFrontRegex = /^\d{6}$/;
        const rrnBackRegex = /^[1-4]$/;

        OX.innerText = "❌";
        isJuminOk = false;

        if (!rrnFrontRegex.test(rrnFront) || !rrnBackRegex.test(rrnBack)) return;

        const yy = parseInt(rrnFront.substring(0, 2), 10);
        const mm = parseInt(rrnFront.substring(2, 4), 10);
        const dd = parseInt(rrnFront.substring(4, 6), 10);

        let fullYear;
        if (rrnBack === "1" || rrnBack === "2") {
            fullYear = 1900 + yy;
        } else if (rrnBack === "3" || rrnBack === "4") {
            fullYear = 2000 + yy;
        } else {
            return;
        }

        const isLeapYear = (fullYear % 4 === 0 && fullYear % 100 !== 0) || (fullYear % 400 === 0);
        const daysInMonth = [31, isLeapYear ? 29 : 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31];

        if (mm < 1 || mm > 12) return;
        if (dd < 1 || dd > daysInMonth[mm - 1]) return;

        if ((fullYear >= 1926 && fullYear <= 1999 && (rrnBack === "1" || rrnBack === "2")) ||
            (fullYear >= 2000 && fullYear <= 2025 && (rrnBack === "3" || rrnBack === "4"))) {
            OX.innerText = "✅";
            isJuminOk = true;
        } else {
            OX.innerText = "❌";
            isJuminOk = false;
        }
    }

    document.querySelector(".generalForm").addEventListener('submit', function (e) {
        if (!isIdOk || !isEmailOk || !isHpOk || !isJuminOk || !reUid.test(id.value) || !rePass.test(password.value) && !isPwOk) {
            e.preventDefault();
            alert('입력값을 확인해주세요.');
        } else {
            alert('회원가입이 완료되었습니다.');
        }
    });
});
