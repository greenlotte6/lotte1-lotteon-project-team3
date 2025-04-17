document.addEventListener('DOMContentLoaded', () => {

    // 열기
    document.querySelector('.orderinfo').addEventListener('click', function () {
        document.getElementById('bannerModal').style.display = 'block';
    });

    // 닫기
    document.querySelector('.modal .close').addEventListener('click', function () {
        document.getElementById('bannerModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function (e) {
        const modal = document.getElementById('bannerModal');
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    //
    // 열기
    document.querySelector('.submitbtn').addEventListener('click', function () {
        document.getElementById('subModal').style.display = 'block';
    });

    // 닫기
    document.querySelector('.submodal .close').addEventListener('click', function () {
        document.getElementById('subModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function (e) {
        const modal = document.getElementById('subModal');
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
});
