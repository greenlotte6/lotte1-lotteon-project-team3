document.addEventListener('DOMContentLoaded', () => {
    const bannerModal = document.getElementById('bannerModal');
    const subModal = document.getElementById('subModal');
    const regModal = document.getElementById('regModal');

    // === 공지 상세 보기 모달 열기 ===
    document.querySelectorAll('.submitbtn')?.forEach(btn => {
        btn.addEventListener('click', () => {
            const type1 = btn.getAttribute('data-type1');
            const type2 = btn.getAttribute('data-type2');
            const title = btn.getAttribute('data-title');
            const writer = btn.getAttribute('data-writer');
            const content = btn.getAttribute('data-content');
            const comment = btn.getAttribute('data-comment');

            document.getElementById('modalType1').textContent = type1;
            document.getElementById('modalType2').textContent = type2;
            document.getElementById('modalTitle').textContent = title;
            document.getElementById('modalWriter').textContent = writer;
            document.getElementById('modalComment').innerHTML = comment;
            document.getElementById('modalContent').innerHTML = content.replace(/\n/g, '<br>');


            bannerModal.style.display = 'block';
        });
    });

    // === 수정 모달 열기
    document.querySelectorAll('.answerbtn').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.stopPropagation();
            regModal.style.display = 'block'; // 수정 모달 띄우기

            // 버튼에서 데이터를 추출해서 수정 모달에 반영
            const cate1 = btn.getAttribute('data-type1');
            const cate2 = btn.getAttribute('data-type2');
            const title = btn.getAttribute('data-title');
            const content = btn.getAttribute('data-content');
            const comment = btn.getAttribute('data-comment');
            const qnaNo = btn.getAttribute('data-qna-no'); // qna 추출
            console.log("qna:", qnaNo);  // 값이 정상적으로 출력되는지 확인

            document.getElementById('modifyTitle').value = title; // 제목 설정
            document.getElementById('modifyContent').value = content; //내용설정
            document.getElementById('modifyComment').value = comment; //내용설정

            // 내용 설정
            const contentArea = document.getElementById('content');

            // 추가: qna 폼에 숨겨진 input으로 추가하여 서버로 전송
            document.getElementById('qnaNo').value = qnaNo; // hidden input에 값 설정
            document.getElementById('modifyCate1').value = cate1;
            document.getElementById('modifyCate2').value = cate2;

            console.log("Setting qnaNo in hidden input: ", document.getElementById('qnaNo').value);
            console.log("Setting qnaNo in hidden input: ", document.getElementById('modifyCate1').value);
            console.log("Setting qnaNo in hidden input: ", document.getElementById('modifyCate2').value);
        }, 0);
    });


    // === 모달 닫기 버튼들
    document.querySelector('#bannerModal .close')?.addEventListener('click', () => bannerModal.style.display = 'none');
    document.querySelector('#subModal .close')?.addEventListener('click', () => subModal.style.display = 'none');
    document.querySelector('#regModal .close')?.addEventListener('click', () => regModal.style.display = 'none');

    // === 모달 외부 클릭 시 닫기
    window.addEventListener('click', (e) => {
        if (e.target === bannerModal) bannerModal.style.display = 'none';
        if (e.target === subModal) subModal.style.display = 'none';
        if (e.target === regModal) regModal.style.display = 'none';
    });

});

