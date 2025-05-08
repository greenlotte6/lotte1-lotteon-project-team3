document.addEventListener("DOMContentLoaded", ()=>{
    const buttons = document.querySelectorAll(".manageBtn");

    buttons.forEach((button)=>{
        button.addEventListener("click", ()=>{
            const statusDiv = button.closest("tr").querySelector(".status-circle");
            if (!statusDiv) return;

            let currentStatus = parseInt(statusDiv.dataset.status);
            let nextStauts = (currentStatus + 1) % 3;

            const shopId = button.closest("tr").querySelector(".shop-id").textContent;
            const status = nextStauts.toString();

            updateStatusInDB(shopId, status);

            updateStatusUI(statusDiv, button, nextStauts);
        });
    })
});

function updateStatusUI(statusDiv, button, status) {
    statusDiv.dataset.status= status;

    if (status === 0 ){
        statusDiv.style.backgroundColor= "blue";
        button.textContent = "[ 승인 ] ";
    }else if (status === 1) {
        statusDiv.style.backgroundColor = "green";
        button.textContent = "[ 중단 ]";
    }else if (status === 2){
        statusDiv.style.backgroundColor= "red";
        button.textContent = "[ 재개 ] ";
    }
}

function updateStatusInDB(shopId, status) {
    const form= new FormData();
    form.append('shopId', shopId);
    form.append('status', status);

    fetch('/admin/shop/update-shop-status',{
        method: 'POST',
        body: form
    })
        .then(response => response.json())
        .then(data => {
            if (data.success){
                console.log('DB 업데이트 성공');
            }else{
                console.log('DB 업데이트 실패');
            }
        })
        .catch(error =>{
            console.log('에러 발생',error);
        });

}