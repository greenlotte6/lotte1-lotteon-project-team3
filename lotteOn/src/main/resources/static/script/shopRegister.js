document.addEventListener('DOMContentLoaded',function () {
// 아이디 유효성 검사 및 중복 체크
document.querySelector("#shopId").addEventListener("input", async function () {
    const shopId = this.value;
    const idMsg = document.querySelector(".idMsg");
    const idRegex = /^[a-zA-Z0-9]{4,12}$/;

    if (!idRegex.test(shopId)) {
        idMsg.textContent = "영문, 숫자 4~12자로 입력하세요.";
        idMsg.style.color = "red";
        return;
    }

    // 아이디 중복 체크 (백엔드에 실제 체크하는 API가 있어야 동작)
    try {
        const res = await fetch(`/api/checkShopId?shopId=${shopId}`);
        const result = await res.json();
        if (result.duplicate) {
            idMsg.textContent = "이미 사용 중인 아이디입니다.";
            idMsg.style.color = "red";
        } else {
            idMsg.textContent = "사용 가능한 아이디입니다.";
            idMsg.style.color = "green";
        }
    } catch (e) {
        idMsg.textContent = "중복 체크 실패";
        idMsg.style.color = "red";
    }
});

// 비밀번호 유효성 검사
document.querySelector("#shopPw").addEventListener("input", function () {
    const pw = this.value;
    const pwMsg = document.querySelector(".pwMsg");
    const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,12}$/;

    if (!pwRegex.test(pw)) {
        pwMsg.textContent = "영문, 숫자, 특수문자 포함 8~12자";
        pwMsg.style.color = "red";
    } else {
        pwMsg.textContent = "사용 가능한 비밀번호입니다.";
        pwMsg.style.color = "green";
    }
});

// 전체 유효성 검사 (form 전송 시 실행)
function validateForm() {
    const shopId = document.querySelector("#shopId").value;
    const shopPw = document.querySelector("#shopPw").value;
    const shopPw2 = document.querySelector("#shopPw2").value;
    const businessNo = document.querySelector("input[name='businessNo']").value;
    const communicationNo = document.querySelector("input[name='communicationNo']").value;

    const idRegex = /^[a-zA-Z0-9]{4,12}$/;
    const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,12}$/;
    const businessRegex = /^\d{3}-\d{2}-\d{5}$/;
    const commRegex = /^\d{4}-[가-힣]{2,}-\d{5}$/;

    if (!idRegex.test(shopId)) {
        alert("아이디를 올바르게 입력하세요.");
        return false;
    }

    if (!pwRegex.test(shopPw)) {
        alert("비밀번호를 올바르게 입력하세요.");
        return false;
    }

    if (shopPw !== shopPw2) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    if (!businessRegex.test(businessNo)) {
        alert("사업자 등록번호는 000-00-00000 형식으로 입력하세요.");
        return false;
    }

    if (!commRegex.test(communicationNo)) {
        alert("통신 판매업 번호는 0000-지역명-00000 형식으로 입력하세요.");
        return false;
    }

    return true;
}
});