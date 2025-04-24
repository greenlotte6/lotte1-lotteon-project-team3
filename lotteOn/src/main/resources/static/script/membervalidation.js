document.addEventListener('DOMContentLoaded', function () {

    const reUid = /^[a-z]+[a-z0-9]{3,11}$/; //4~12자
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;

    let isIdOk = false;
    let isPwOk = false;

    const id = document.getElementById('id');
    const password = document.getElementById('password');
    const password2 = document.getElementById('password2');
    const idMsg = document.querySelector('.idMsg');
    const pwMsg = document.querySelector('.pwMsg');

    let idTimeout = null;

//아이디 실시간 유효성검사 및 중복체크
    id.addEventListener('input', () => {
        clearTimeout(idTimeout); //이전 타이머 제거
        idTimeout = setTimeout(checkSellerId, 300);
    });

    async function checkSellerId() {
        const value = id.value;

        if (!reUid.test(value)) {
            idMsg.innerText = '아이디는 영문으로 시작하는 4~12자여야 합니다.';
            idMsg.style.color = 'red';
            isIdOk = false;
            return;
        }
        try {
            const response = await fetch(`/member/check-member-id/${value}`);
            const data = await response.json();

            if (data.exists) {
                idMsg.innerText = '이미 사용중인 아이디입니다.';
                idMsg.style.color = 'red';
                isIdOk = false;
            } else {
                idMsg.innerText = '사용 가능한 아이디입니다.';
                idMsg.style.color = 'green';
                isIdOk = true;
            }
        } catch (err) {
            idMsg.innerText = '서버 오류';
            idMsg.style.color = 'red';
            isIdOk = false;
        }
    }

//비밀번호 유효성 검사
    password2.addEventListener('keyup', () => {
        const pw1 = password.value;
        const pw2 = password2.value;

        if (!rePass.test(pw1)) {
            pwMsg.innerText = '비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다.';
            pwMsg.style.color = 'red';
            isPwOk = false;
            return;
        }

        if (pw1 !== pw2) {
            pwMsg.innerText = '비밀번호가 일치하지 않습니다.';
            pwMsg.style.color = 'red';
            isPwOk = false;
        } else {
            pwMsg.innerText = '사용 가능한 비밀번호입니다.';
            pwMsg.style.color = 'green';
            isPwOk = true;
        }
    });
//회원가입 전송 시 유효성 검사
    document.querySelector('form').addEventListener('submit', function (e) {
        if (!isIdOk || !isPwOk || !reUid.test(id.value) || !rePass.test(password.value)){
            e.preventDefault();
            alert('입력값을 확인해주세요.');
        }
    });
});