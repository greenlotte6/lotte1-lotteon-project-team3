document.addEventListener('DOMContentLoaded', function () {
    const hp = document.querySelector('input[name="hp"]');
    const pass1 = document.querySelector(".password1");
    const pass2 = document.querySelector(".password2");
    const realPassword = document.querySelector(".realPassword");
    const passMsg1 = document.querySelector(".passMsg1");
    const passMsg2 = document.querySelector(".passMsg2");
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;
    let isPwOk = false;

    hp.addEventListener('input', function(e) {
        let number = e.target.value.replace(/[^0-9]/g, '');

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

    pass1.addEventListener('keyup', () => {
        const pw1 = pass1.value;
        if (pw1 === '') {
            realPassword.value = realPassword.dataset.original;
            passMsg1.innerText = '';
            passMsg2.innerText = '';
            isPwOk = true;
            return;
        }

        if (!rePass.test(pw1)) {
            passMsg1.innerText = '비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다';
            passMsg1.style.color = '#E60012';
            isPwOk = false;
        } else {
            passMsg1.innerText = '';
        }
    });


    pass2.addEventListener('keyup', () => {
        const pw1 = pass1.value;
        const pw2 = pass2.value;

        if (pw1 === '' && pw2 === '') {
            realPassword.value = realPassword.dataset.original;
            passMsg1.innerText = '';
            passMsg2.innerText = '';
            isPwOk = true;
            return;
        }

        if (pw1 !== pw2) {
            passMsg2.innerText = '비밀번호가 일치하지 않습니다.';
            passMsg2.style.color = '#E60012';
            isPwOk = false;
        } else {
            passMsg2.innerText = '';
            realPassword.value = pw2;
            isPwOk = true;
        }
    });

    document.querySelector("#myProfile").addEventListener('submit', function (e) {
        const pw1 = pass1.value;
        const pw2 = pass2.value;

        if (pw1 === '' && pw2 === '') {
            realPassword.disabled = true;

            if (!confirm('수정하시겠습니까?')) {
                e.preventDefault();
            }
            return;
        }

        if (!rePass.test(pw1)) {
            e.preventDefault();
            alert('비밀번호를 확인해 주세요.');
            return;
        }

        if (pw1 !== pw2) {
            e.preventDefault();
            alert('비밀번호를 확인해 주세요.');
            return;
        }

        realPassword.value = pw2;

        if (!confirm('수정하시겠습니까?')) {
            e.preventDefault();
        } else {
            alert('수정이 완료되었습니다.');
        }
    });
});