/*
document.addEventListener('DOMContentLoaded', () => {
    const bannerModal = document.getElementById('bannerModal');
    const subModal = document.getElementById('subModal');
    const regModal = document.getElementById('regModal');

    // === 모달 열기 버튼들 ===
    document.querySelector('.submitbtn')?.addEventListener('click', () => {
        bannerModal.style.display = 'block';
    });

    document.querySelector('.regbutton')?.addEventListener('click', (e) => {
        e.stopPropagation();
        subModal.style.display = 'block';
    });

    document.querySelector('.modifybtn')?.addEventListener('click', (e) => {
        e.stopPropagation();
        bannerModal.style.display = 'none';
        regModal.style.display = 'block';
    });

    // === 모달 닫기 버튼들 ===
    document.querySelector('#bannerModal .close')?.addEventListener('click', () => {
        bannerModal.style.display = 'none';
    });

    document.querySelector('#subModal .close')?.addEventListener('click', () => {
        subModal.style.display = 'none';
    });

    document.querySelector('#regModal .close')?.addEventListener('click', () => {
        regModal.style.display = 'none';
    });

    // === 모달 외부 클릭 시 닫기 (한 이벤트로 처리)
    window.addEventListener('click', (e) => {
        if (e.target === bannerModal) bannerModal.style.display = 'none';
        if (e.target === subModal) subModal.style.display = 'none';
        if (e.target === regModal) regModal.style.display = 'none';
    });
});

 */
document.addEventListener('DOMContentLoaded', () => {
    const bannerModal = document.getElementById('bannerModal');
    const subModal = document.getElementById('subModal');
    const regModal = document.getElementById('regModal');

    // === 모달 열기 버튼들 ===
    document.querySelector('.submitbtn')?.addEventListener('click', () => {
        bannerModal.style.display = 'block';
    });

    document.querySelector('.regbutton')?.addEventListener('click', (e) => {
        e.stopPropagation();
        subModal.style.display = 'block';
    });

    document.querySelectorAll('.modifybtn').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.stopPropagation();
            bannerModal.style.display = 'none';
            regModal.style.display = 'block';
        });
    });

    // === 모달 닫기 버튼들 ===
    document.querySelector('#bannerModal .close')?.addEventListener('click', () => {
        bannerModal.style.display = 'none';
    });

    document.querySelector('#subModal .close')?.addEventListener('click', () => {
        subModal.style.display = 'none';
    });

    document.querySelector('#regModal .close')?.addEventListener('click', () => {
        regModal.style.display = 'none';
    });

    // === 모달 외부 클릭 시 닫기 (한 이벤트로 처리)
    window.addEventListener('click', (e) => {
        if (e.target === bannerModal) bannerModal.style.display = 'none';
        if (e.target === subModal) subModal.style.display = 'none';
        if (e.target === regModal) regModal.style.display = 'none';
    });
});