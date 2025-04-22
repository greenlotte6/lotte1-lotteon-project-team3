document.addEventListener('DOMContentLoaded', () => {
    const checkAll = document.getElementById('checkAll');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]:not(#checkAll)');

    //추가
    const form= document.querySelector('form');
    const submitButton= form.querySelector('button[type="submit"]');


    checkAll.addEventListener('change', () => {
        checkboxes.forEach(cb => cb.checked = checkAll.checked);
    });

    //제출 전 체크 확인
    form.addEventListener('submit',(e) =>{
        //모든 개별 동의 항목이  체크되어 있는지 확인
        const allChecked = Array.from(checkboxes).every(cb => cb.checked);

        if(!allChecked){
            alert('모든 약관에 동의해야 합니다.');
            e.preventDefault();
        }
    });
});