document.addEventListener('DOMContentLoaded', function () {

    const reUid = /^[a-z]+[a-z0-9]{3,11}$/; // 4~12자 아이디
    const rePass = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,12}$/;

    let isIdOk = false;
    let isPwOk = false;
    let isCompanyNameOk = false;
    let isBusinessNoOk = false;
    let isCommunicationNoOk = false;
    let isHpOk = false;
    let isFaxOk = false;

    // 요소 가져오기
    const sellerId = document.getElementById('sellerId');
    const sellerPw = document.getElementById('sellerPw');
    const sellerPw2 = document.getElementById('sellerPw2');
    const companyName = document.getElementById('companyName');
    const businessNo = document.getElementById('businessNo');
    const communicationNo = document.getElementById('communicationNo');
    const hp = document.getElementById('hp'); // 여기 hp로 id가 되어있어요
    const fax = document.getElementById('fax');
    const delegate= document.getElementById('delegate');

    const sIdMsg = document.querySelector('.sIdMsg');
    const sPwMsg = document.querySelector('.sPwMsg');
    const sBusinessMsg = document.querySelector('.sBusinessMsg');
    const sCommunicationMsg = document.querySelector('.sCommunicationMsg');
    const sCompanyMsg= document.querySelector('.sCompanyMsg');
    const sFaxMsg= document.querySelector('.sFaxMsg');
    const sHpMsg= document.querySelector('.sHpMsg');
    const sDelegateMsg = document.querySelector('.sDelegateMsg');


    let idTimeout = null;
    let companyTimeout = null;
    let businessTimeout = null;
    let communicationTimeout = null;
    let hpTimeout = null;
    let faxTimeout = null;
    let delegateTimeout= null;


    // 아이디 검사
    sellerId.addEventListener('input', () => {
        clearTimeout(idTimeout);
        idTimeout = setTimeout(checkSellerId, 300);
    });

    async function checkSellerId() {
        const value = sellerId.value;

        if (!reUid.test(value)) {
            sIdMsg.innerText = '아이디는 영문으로 시작하는 4~12자여야 합니다';
            sIdMsg.style.color = '#E60012';
            isIdOk = false;
            return;
        }
        try {
            const response = await fetch(`/member/check-id/${value}`);
            const data = await response.json();

            if (data.exists) {
                sIdMsg.innerText = '이미 사용중인 아이디입니다';
                sIdMsg.style.color = '#E60012';
                isIdOk = false;
            } else {
                sIdMsg.innerText = '';
                isIdOk = true;
            }
        } catch (err) {
            sIdMsg.innerText = '서버 오류';
            sIdMsg.style.color = '#E60012';
            isIdOk = false;
        }
    }

    // 비밀번호 검사
    sellerPw.addEventListener('keyup', () => {
        const pw1 = sellerPw.value;

        if (!rePass.test(pw1)) {
            sPwMsg.innerText = '비밀번호는 영문+숫자+특수문자 포함 8~12자여야 합니다';
            sPwMsg.style.color = '#E60012';
            isPwOk = false;
            return;
        } else {
            sPwMsg.innerText = '';
        }
    });

    sellerPw2.addEventListener('keyup', () => {
        const pw1 = sellerPw.value;
        const pw2 = sellerPw2.value;

        if (pw1 !== pw2) {
            sPwMsg.innerText = '비밀번호가 일치하지 않습니다.';
            sPwMsg.style.color = '#E60012';
            isPwOk = false;
        } else {
            sPwMsg.innerText = '';
            isPwOk = true;
        }
    });

    //회사명 중복성 검사
    companyName.addEventListener('input', () => {
        clearTimeout(companyTimeout);
        companyTimeout = setTimeout(checkCompanyName, 300);
    });

    async function checkCompanyName() {
        const value = companyName.value;

        try {
            const response = await fetch(`/member/checkCompanyName/${encodeURIComponent(value)}`);
            const data = await response.json();

            if (data.exists) {
                sCompanyMsg.innerText = '이미 등록된 회사명입니다';
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

    // 사업자번호 검사
    businessNo.addEventListener('input', (e) => {
        clearTimeout(businessTimeout);
        businessTimeout = setTimeout(checkBusinessNo, 300);
    });

    async function checkBusinessNo() {
        const value = businessNo.value;
        const pattern = /^\d{3}-\d{2}-\d{5}$/;

        if (!pattern.test(value)) {
            sBusinessMsg.innerText = '형식에 맞게 입력해주세요 (예: 123-45-67890)';
            sBusinessMsg.style.color = '#E60012';
            isBusinessNoOk = false;
            return;
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

    // 통신판매업번호 검사
    communicationNo.addEventListener('input', (e) => {
        clearTimeout(communicationTimeout);
        communicationTimeout = setTimeout(checkCommunicationNo, 300);
    });

    async function checkCommunicationNo() {
        const value = communicationNo.value;
        const pattern = /^\d{3}-\d{2}-\d{5}$/; // 사업자 등록번호와 같은 형식

        if (!pattern.test(value)) {
            sCommunicationMsg.innerText = '형식에 맞게 입력해주세요 (예: 123-45-67890)';
            sCommunicationMsg.style.color = '#E60012';
            isCommunicationNoOk = false;
            return;
        }

        try {
            const response = await fetch(`/member/checkCommunicationNo/${value}`);
            const data = await response.json();

            if (data.exists) {
                sCommunicationMsg.innerText = '이미 등록된 통신판매업 번호입니다';
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

        //휴대폰 번호 검사
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
                    sHpMsg.innerText = '이미 등록된 휴대폰 번호입니다';
                    sHpMsg.style.color = '#E60012';
                    isHpOk = false;
                } else {
                    sHpMsg.innerText = '';
                    isHpOk = true;
                }
            } catch (err) {
                sHpMsg.innerText = '서버 오류';
                sHpMsg.style.color = '#E60012';
                isHpOk = false;
            }
        }



    //대표자 검사
    delegate.addEventListener('input', () => {
        clearTimeout(delegateTimeout);
        delegateTimeout = setTimeout(checkDelegate, 300);
    });

    async function checkDelegate() {
        const value = delegate.value.trim();

        if (value.length === 0) {
            sDelegateMsg.innerText = '대표자명을 입력해주세요.';
            sDelegateMsg.style.color = '#E60012';
            isDelegateOk = false;
            return;
        }

        try {
            const response = await fetch(`/member/checkDelegate/${encodeURIComponent(value)}`);
            const data = await response.json();

            if (data.exists) {
                sDelegateMsg.innerText = '이미 등록된 대표자입니다';
                sDelegateMsg.style.color = '#E60012';
                isDelegateOk = false;
            } else {
                sDelegateMsg.innerText = '';
                isDelegateOk = true;
            }
        } catch (err) {
            sDelegateMsg.innerText = '서버 오류';
            sDelegateMsg.style.color = '#E60012';
            isDelegateOk = false;
        }
    }


    //팩스 검사
    fax.addEventListener('input', () => {
        clearTimeout(faxTimeout);
        faxTimeout = setTimeout(checkFax, 300);
    });

    async function checkFax() {
        const value = fax.value;

        try {
            const response = await fetch(`/member/checkFax/${value}`);
            const data = await response.json();

            if (data.exists) {
                sFaxMsg.innerText = '이미 등록된 팩스 번호입니다';
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



    // 최종 form submit 검사
    const form = document.querySelector('#bannerModal form');

    form.addEventListener('submit', function (e) {
        if (!isIdOk || !isPwOk || !isBusinessNoOk || !isCommunicationNoOk || !isHpOk || !isFaxOk || !isCompanyNameOk) {
            e.preventDefault();
            alert('입력값을 다시 확인해주세요.');
        }
    });

});
