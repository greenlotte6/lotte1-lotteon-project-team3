document.addEventListener('DOMContentLoaded', function () {

    const reUid = /^[a-z]+[a-z0-9]{3,11}$/; //4~12자
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;

    let isIdOk = false;
    let isPwOk = false;

    const sellerId = document.getElementById('sellerId');
    const sellerPw = document.getElementById('sellerPw');
    const sellerPw2 = document.getElementById('sellerPw2');
    const idMsg = document.querySelector('.idMsg');
    const pwMsg = document.querySelector('.pwMsg');

    let idTimeout = null;

//아이디 실시간 유효성검사 및 중복체크
    sellerId.addEventListener('input', () => {
        clearTimeout(idTimeout); //이전 타이머 제거
        idTimeout = setTimeout(checkSellerId, 300);
    });

    async function checkSellerId() {
        const value = sellerId.value;

        if (!reUid.test(value)) {
            idMsg.innerText = '아이디는 영문으로 시작하는 4~12자여야 합니다.';
            idMsg.style.color = 'red';
            isIdOk = false;
            return;
        }
        try {
            const response = await fetch(`/member/check-id/${value}`);
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
    sellerPw2.addEventListener('keyup', () => {
        const pw1 = sellerPw.value;
        const pw2 = sellerPw2.value;

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

    //사업자 등록번호 유효성 검사
    const bisNum= document.getElementById('bisNum');
    const bisMsg= document.querySelector('.bisNumMsg');

    bisNum.addEventListener('input', ()=>{
        const value= bisNum.value;
        const pattern= /^\d{3}-\d{2}-\d{5}$/;

        if(!pattern.test(value)){
            bisMsg.innerText= '형식에 맞게 입력해주세요 (예: 123-45-67890)';
            bisMsg.style.color= 'red';
        }else{
            bisMsg.innerText= '올바른 사업자 등록번호 형식입니다.';
            bisMsg.style.color= 'green';
        }
    });

//회원가입 전송 시 유효성 검사
    document.querySelector('form').addEventListener('submit', function (e) {
        if (!isIdOk || !isPwOk || !reUid.test(sellerId.value) || !rePass.test(sellerPw.value)){
            e.preventDefault();
            alert('입력값을 확인해주세요.');
        }
    });
});