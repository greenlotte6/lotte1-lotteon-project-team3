// document.addEventListener('DOMContentLoaded', function (){
//     const deleteKeyword = document.querySelector(".deleteSearchKeyword");
//     const inputSearch = document.querySelector(".inputSearch");
//
//     if(inputSearch.value) {
//         deleteKeyword.style.display = "block";
//     } else {
//         deleteKeyword.style.display = "none";
//     }
//
//     inputSearch.addEventListener("input", function () {
//         if(this.value) {
//             deleteKeyword.style.display = "block";
//         } else {
//             deleteKeyword.style.display = "none";
//         }
//     });
//
//     deleteKeyword.addEventListener("click", function () {
//         inputSearch.value = "";
//         deleteKeyword.style.display = "none";
//         inputSearch.focus();
//     });
// })
document.addEventListener('DOMContentLoaded', function () {
    const deleteKeyword = document.querySelector(".deleteSearchKeyword");
    const inputSearch = document.querySelector(".inputSearch");
    const autoList = document.querySelector("#autoList");

    const renderAutoList = (data) => {
        autoList.innerHTML = data.map(d => `<li class="suggestion-item">${d}</li>`).join("");
    };

    inputSearch.addEventListener("input", async function () {
        const keyword = this.value;
        if (keyword.length > 1) {
            deleteKeyword.style.display = "block";

            const res = await fetch(`/product/api/search/auto?keyword=${keyword}`);
            const data = await res.json();
            renderAutoList(data);
        } else {
            deleteKeyword.style.display = "none";
            autoList.innerHTML = "";
        }
    });

    deleteKeyword.addEventListener("click", function () {
        inputSearch.value = "";
        deleteKeyword.style.display = "none";
        autoList.innerHTML = "";
        inputSearch.focus();
    });

    autoList.addEventListener("click", function (e) {
        if (e.target.classList.contains("suggestion-item")) {
            inputSearch.value = e.target.innerText;
            autoList.innerHTML = "";
            document.querySelector("#searchForm").submit();
        }
    });
});


