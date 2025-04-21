function execDaumpostcode() {
    new daum.Postcode({
        oncomplete: function (data) {

            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            } else {

            }

            // 주소 정보를 받아온 후 input에 자동으로 채워줌
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('address').value = data.roadAddress || data.jibunAddress;
            document.getElementById('address2').focus();
        }
    }).open();
}