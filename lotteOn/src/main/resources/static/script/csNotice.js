document.addEventListener('DOMContentLoaded', () => {
    const bannerModal = document.getElementById('bannerModal');
    const subModal = document.getElementById('subModal');
    const regModal = document.getElementById('regModal');

    // === 공지 상세 보기 모달 열기 ===
    document.querySelectorAll('.submitbtn')?.forEach(btn => {
        btn.addEventListener('click', () => {
            const type = btn.getAttribute('data-type');
            const title = btn.getAttribute('data-title');
            const content = btn.getAttribute('data-content');

            document.getElementById('modalType').textContent = type;
            document.getElementById('modalTitle').textContent = title;
            document.getElementById('modalContent').innerHTML = content.replace(/\n/g, '<br>');

            bannerModal.style.display = 'block';
        });
    });

    // === 글 작성 모달 열기 (카테고리 선택 상태 유지) ===
    document.querySelector('.regbutton')?.addEventListener('click', (e) => {
        e.stopPropagation();
        subModal.style.display = 'block';

        // 현재 URL에서 cate 파라미터 추출 (예: ?cate=고객서비스)
        const urlParams = new URLSearchParams(window.location.search);
        const selectedCate = urlParams.get('cate');

        // 글작성 모달 내 select 요소에 해당 값 자동 선택
        if (selectedCate) {
            const cateSelect = subModal.querySelector('select[name="cate"]');
            if (cateSelect) {
                [...cateSelect.options].forEach(opt => {
                    if (opt.value === selectedCate) {
                        opt.selected = true;
                    }
                });
            }
        }
    });

    // === 수정 모달 열기
    document.querySelectorAll('.modifybtn').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.stopPropagation();
            regModal.style.display = 'block'; // 수정 모달 띄우기

            // 버튼에서 데이터를 추출해서 수정 모달에 반영
            const type = btn.getAttribute('data-type');
            const title = btn.getAttribute('data-title');
            const content = btn.getAttribute('data-content');
            const noticeNo = btn.getAttribute('data-notice-no'); // noticeNo 추출
            console.log("noticeNo:", noticeNo);  // 값이 정상적으로 출력되는지 확인

            // 수정 폼에 데이터 채우기
            document.getElementById('modifyCate').value = type;  // 카테고리 설정
            document.getElementById('modifyTitle').value = title; // 제목 설정
            document.getElementById('modifyContent').value = content; //내용설정

            // 내용 설정
            const contentArea = document.getElementById('content');

            // 추가: noticeNo를 폼에 숨겨진 input으로 추가하여 서버로 전송
            document.getElementById('noticeNo').value = noticeNo; // hidden input에 값 설정
            console.log("Setting noticeNo in hidden input: ", document.getElementById('noticeNo').value);
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

