document.addEventListener('DOMContentLoaded', function() {
    const iamGeneral = document.querySelector(".iamGeneral");
    const iamSeller = document.querySelector(".iamSeller");
    const generalForm = document.querySelector(".generalForm");
    const sellerForm = document.querySelector(".sellerForm");

    const clearGeneralForm = document.querySelectorAll(".generalForm input");
    const clearSellerForm = document.querySelectorAll(".sellerForm input");
    const clearMsgs = document.querySelectorAll(".msgs");
    const clearRadio = document.querySelectorAll("input[name=gender]");

    const allCheck1 = document.querySelectorAll('.termsCheck.all1');
    const checks1 = document.querySelectorAll('.termsCheckBox .check1');

    const allCheck2 = document.querySelectorAll('.termsCheck.all2');
    const checks2 = document.querySelectorAll('.termsCheckBox .check2');

    iamGeneral.addEventListener("click", function() {
        iamGeneral.classList.add("iam");
        iamSeller.classList.remove("iam");
        generalForm.style.display = "block";
        sellerForm.style.display = "none";

        clearSellerForm.forEach(element => {
            element.value = "";
        });

        clearMsgs.forEach(element => {
            element.innerText = "";
        });

        allCheck1.forEach(element => {
           element.checked = false;
        });

        checks1.forEach(element => {
           element.checked = false;
        });
    });

    iamSeller.addEventListener("click", function() {
        iamSeller.classList.add("iam");
        iamGeneral.classList.remove("iam");
        sellerForm.style.display = "block";
        generalForm.style.display = "none";

        clearGeneralForm.forEach(element => {
            element.value = "";
        });

        clearRadio.forEach(element => {
            element.checked = false;
        });

        clearMsgs.forEach(element => {
            element.innerText = "";
        });

        allCheck2.forEach(element => {
            element.checked = false;
        });

        checks2.forEach(element => {
            element.checked = false;
        });
    });


    checks1.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            if (Array.from(checks1).every(checkbox => checkbox.checked)) {
                allCheck1.forEach(allCheckBox => {
                    allCheckBox.checked = true;
                });
            } else {
                allCheck1.forEach(allCheckBox => {
                    allCheckBox.checked = false;
                });
            }
        });
    });

    allCheck1.forEach(allCheckBox => {
        allCheckBox.addEventListener('change', (e) => {
            checks1.forEach(checkbox => {
                checkbox.checked = e.target.checked;
            });
        });
    });

    checks2.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            if (Array.from(checks2).every(checkbox => checkbox.checked)) {
                allCheck2.forEach(allCheckBox => {
                    allCheckBox.checked = true;
                });
            } else {
                allCheck2.forEach(allCheckBox => {
                    allCheckBox.checked = false;
                });
            }
        });
    });

    allCheck2.forEach(allCheckBox => {
        allCheckBox.addEventListener('change', (e) => {
            checks2.forEach(checkbox => {
                checkbox.checked = e.target.checked;
            });
        });
    });
});