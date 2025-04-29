document.addEventListener('DOMContentLoaded', () => {

    // 열기
    document.querySelectorAll('.orderinfo').forEach(btn => {
        btn.addEventListener('click', function () {
            // 버튼에서 data-* 속성 값 가져오기
            const type = btn.getAttribute('data-type');
            const title = btn.getAttribute('data-title');
            const content = btn.getAttribute('data-content');
            const company = btn.getAttribute('data-company');
            const benefit = btn.getAttribute('data-benefit');
            const startDate = btn.getAttribute('data-startDate');
            const endDate = btn.getAttribute('data-endDate');
            const couponCode = btn.getAttribute('data-couponCode');

            // 모달에 해당 정보 설정
            document.getElementById('modalType').textContent = type;
            document.getElementById('modalTitle').textContent = title;
            document.getElementById('modalContent').innerHTML = content.replace(/\n/g, '<br>');
            document.getElementById('modalCompany').textContent = company;
            document.getElementById('modalBenefit').textContent = benefit;
            document.getElementById('modalStartDate').textContent = startDate;
            document.getElementById('modalEndDate').textContent = endDate;
            document.getElementById('modalCouponCode').textContent = couponCode;

            // 모달 창 표시
            document.getElementById('bannerModal').style.display = 'block';
        });
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
