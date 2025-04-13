document.addEventListener('DOMContentLoaded', function (){
    const deleteKeyword = document.querySelector(".deleteSearchKeyword");
    const inputSearch = document.querySelector(".inputSearch");

    if(inputSearch.value) {
        deleteKeyword.style.display = "block";
    } else {
        deleteKeyword.style.display = "none";
    }

    inputSearch.addEventListener("input", function () {
        if(this.value) {
            deleteKeyword.style.display = "block";
        } else {
            deleteKeyword.style.display = "none";
        }
    });

    deleteKeyword.addEventListener("click", function () {
        inputSearch.value = "";
        deleteKeyword.style.display = "none";
        inputSearch.focus();
    });
})

