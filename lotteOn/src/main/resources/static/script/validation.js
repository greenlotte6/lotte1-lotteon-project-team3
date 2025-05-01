document.addEventListener('DOMContentLoaded', function () {

    const reSid = /^[a-z]+[a-z0-9]{3,11}$/; //4~12자
    const reSPass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;

    let isSidOk = false;
    let isSpwOk = false;
    let isCompanyNameOk = false;
    let isBusinessNoOk = false;
    let isCommunicationNoOk = false;
    let isSHpOk = false;
    let isFaxOk = false;

    const sellerId = document.getElementById('sellerId');
    const sellerPw = document.getElementById('sellerPw');
    const sellerPw2 = document.getElementById('sellerPw2');
    const companyName = document.getElementById('companyName');
    const businessNo = document.getElementById('businessNo');
    const communicationNo = document.getElementById('communicationNo');
    const sHp = document.getElementById('sHp');
    const fax = document.getElementById('fax');

    const sIdMsg = document.querySelector('.sIdMsg');
    const sPwMsg = document.querySelector('.sPwMsg');
    const sPw2Msg = document.querySelector('.sPw2Msg');
    const sCompanyMsg = document.querySelector('.sCompanyMsg');
    const sBusinessMsg = document.querySelector('.sBusinessMsg');
    const sCommunicationMsg = document.querySelector('.sCommunicationMsg');
    const sHpMsg = document.querySelector('.sHpMsg');
    const sFaxMsg = document.querySelector('.sFaxMsg');

    let idTimeout = null;
    let companyTimeout = null;
    let businessTimeout = null;
    let communicationTimeout = null;
    let hpTimeout = null;
    let faxTimeout = null;

    //아이디 실시간 유효성검사 및 중복체크
    sellerId.addEventListener('input', () => {
        clearTimeout(idTimeout); //이전 타이머 제거
        idTimeout = setTimeout(checkSellerId, 300);
    });

    async function checkSellerId() {
        const value = sellerId.value;

        if (!reSid.test(value)) {
            sIdMsg.innerText = '아이디는 영문으로 시작하는 4~12자여야 합니다';
            sIdMsg.style.color = '#E60012';
            isSidOk = false;
            return;
        }
        try {
            const response = await fetch(`/member/check-id/${value}`);
            const data = await response.json();

            if (data.exists) {
                sIdMsg.innerText = '이미 사용중인 아이디입니다';
                sIdMsg.style.color = '#E60012';
                isSidOk = false;
            } else {
                sIdMsg.innerText = '';
                isSidOk = true;
            }
        } catch (err) {
            sIdMsg.innerText = '서버 오류';
            sIdMsg.style.color = '#E60012';
            isSidOk = false;
        }
    }

    //비밀번호 유효성 검사
    sellerPw.addEventListener('keyup', () => {
        const pw1 = sellerPw.value;

        if (!reSPass.test(pw1)) {
            sPwMsg.innerText = '비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다';
            sPwMsg.style.color = '#E60012';
            isSpwOk = false;
            return;
        } else {
            sPwMsg.innerText = '';
        }
    });

    sellerPw2.addEventListener('keyup', () => {
        const pw1 = sellerPw.value;
        const pw2 = sellerPw2.value;

        if (pw1 !== pw2) {
            sPw2Msg.innerText = '비밀번호가 일치하지 않습니다.';
            sPw2Msg.style.color = '#E60012';
            isSpwOk = false;
        } else {
            sPw2Msg.innerText = '';
            isSpwOk = true;
        }
    });

    companyName.addEventListener('input', () => {
        clearTimeout(companyTimeout); //이전 타이머 제거
        companyTimeout = setTimeout(checkCompanyName, 300);
    });

    async function checkCompanyName() {
        const value = companyName.value;

        try {
            const response = await fetch(`/member/checkCompanyName/${value}`);
            const data = await response.json();

            if (data.exists) {
                sCompanyMsg.innerText = '이미 가입된 회사입니다';
                sCompanyMsg.style.color = '#E60012';
                isCompanyNameOk = false;
            } else {
                sCompanyMsg.innerText = '';
                isCompanyNameOk = true;
            }
        } catch (err) {
            sCompanyMsg.innerText = '서버 오류';
            sCompanyMsg.style.color = '#E60012';
            isCompanyNameOk = false;
        }
    }

    businessNo.addEventListener('input', (e) => {
        clearTimeout(businessTimeout); //이전 타이머 제거
        businessTimeout = setTimeout(checkBusinessNo, 300);
    });

    async function checkBusinessNo() {
        const value = businessNo.value;
        const pattern = /^\d{3}-\d{2}-\d{5}$/;

        if (!pattern.test(value)){
            sBusinessMsg.innerText= '형식에 맞게 입력해주세요 (예: 123-45-67890)';
            sBusinessMsg.style.color= '#E60012';
        } else {
            sBusinessMsg.innerText= '';
        }

        try {
            const response = await fetch(`/member/checkBusinessNo/${value}`);
            const data = await response.json();

            if (data.exists) {
                sBusinessMsg.innerText = '이미 가입된 사업자 번호입니다';
                sBusinessMsg.style.color = '#E60012';
                isBusinessNoOk = false;
            } else {
                sBusinessMsg.innerText = '';
                isBusinessNoOk = true;
            }
        } catch (err) {
            sBusinessMsg.innerText = '서버 오류';
            sBusinessMsg.style.color = '#E60012';
            isBusinessNoOk = false;
        }
    }

    businessNo.addEventListener('input', function(e) {
        let number = e.target.value.replace(/[^0-9]/g, '');

        let formattedNumber = '';
        if (number.length < 4) {
            formattedNumber = number;
        } else if (number.length < 6) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3);
        } else if (number.length < 11) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 5) + '-' + number.slice(5);
        } else {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 5) + '-' + number.slice(5, 10);
        }
        e.target.value = formattedNumber;
    });

    communicationNo.addEventListener('input', () => {
        clearTimeout(communicationTimeout); //이전 타이머 제거
        communicationTimeout = setTimeout(checkCommunicationNo, 300);
    });

    async function checkCommunicationNo() {
        const value = communicationNo.value;

        try {
            const response = await fetch(`/member/checkCommunicationNo/${value}`);
            const data = await response.json();

            if (data.exists) {
                sCommunicationMsg.innerText = '이미 가입된 통신판매업 번호입니다';
                sCommunicationMsg.style.color = '#E60012';
                isCommunicationNoOk = false;
            } else {
                sCommunicationMsg.innerText = '';
                isCommunicationNoOk = true;
            }
        } catch (err) {
            sCommunicationMsg.innerText = '서버 오류';
            sCommunicationMsg.style.color = '#E60012';
            isCommunicationNoOk = false;
        }
    }

    communicationNo.addEventListener('input', function(e) {
        let number = e.target.value.replace(/[^0-9]/g, ''); // 숫자만 남기기

        let formattedNumber = '';
        if (number.length < 4) {
            formattedNumber = number;
        } else if (number.length < 6) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3);
        } else if (number.length < 12) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 5) + '-' + number.slice(5);
        } else {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 5) + '-' + number.slice(5, 11);
        }
        e.target.value = formattedNumber;
    });

    sHp.addEventListener('input', () => {
        clearTimeout(hpTimeout); //이전 타이머 제거
        hpTimeout = setTimeout(checkSHp, 300);
    });

    async function checkSHp() {
        const value = sHp.value;

        try {
            const response = await fetch(`/member/checkSHp/${value}`);
            const data = await response.json();

            if (data.exists) {
                sHpMsg.innerText = '이미 가입된 전화번호입니다';
                sHpMsg.style.color = '#E60012';
                isSHpOk = false;
            } else {
                sHpMsg.innerText = '';
                isSHpOk = true;
            }
        } catch (err) {
            sHpMsg.innerText = '서버 오류';
            sHpMsg.style.color = '#E60012';
            isSHpOk = false;
        }
    }

    sHp.addEventListener('input', function(e) {
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

    fax.addEventListener('input', () => {
        clearTimeout(faxTimeout); //이전 타이머 제거
        faxTimeout = setTimeout(checkFax, 300);
    });

    async function checkFax() {
        const value = fax.value;

        try {
            const response = await fetch(`/member/checkFax/${value}`);
            const data = await response.json();

            if (data.exists) {
                sFaxMsg.innerText = '이미 가입된 팩스번호입니다';
                sFaxMsg.style.color = '#E60012';
                isFaxOk = false;
            } else {
                sFaxMsg.innerText = '';
                isFaxOk = true;
            }
        } catch (err) {
            sFaxMsg.innerText = '서버 오류';
            sFaxMsg.style.color = '#E60012';
            isFaxOk = false;
        }
    }

    fax.addEventListener('input', function(e) {
        let number = e.target.value.replace(/[^0-9]/g, '');

        let formattedNumber = '';
        if (number.length < 4) {
            formattedNumber = number;
        } else if (number.length < 7) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3);
        } else if (number.length < 11) {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 7) + '-' + number.slice(7);
        } else {
            formattedNumber = number.slice(0, 3) + '-' + number.slice(3, 7) + '-' + number.slice(7, 11);
        }
        e.target.value = formattedNumber;
    });

    //회원가입 전송 시 유효성 검사
    document.querySelector(".sellerForm").addEventListener('submit', function (e) {
        if (!isSidOk || !isSpwOk || !isCompanyNameOk || !isBusinessNoOk || !isCommunicationNoOk || !isSHpOk || !isFaxOk || !reSid.test(sellerId.value) || !reSPass.test(sellerPw.value)){
            e.preventDefault();
            alert('입력값을 확인해주세요.');
        } else {
            alert('가입이 완료되었습니다.');
        }
    });
});