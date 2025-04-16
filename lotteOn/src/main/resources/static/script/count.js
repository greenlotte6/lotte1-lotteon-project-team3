document.addEventListener('DOMContentLoaded', function() {
    const plus = document.querySelector(".plus");
    const minus = document.querySelector(".minus");
    const count = document.querySelector(".count");
    const multi = document.querySelector(".multi > p");
    const multi2 = document.querySelector(".multi > del");
    const realTotalPrice = document.querySelector(".totalPrice");

    let currentCount = 1;
    let rawText = multi.innerText;
    let rawText2 = multi2.innerText;
    let cleanedText = rawText.replace(/[,원]/g, "");
    let cleanedText2 = rawText2.replace(/[,원]/g, "");
    let total = parseInt(cleanedText);
    let total2 = parseInt(cleanedText2);

    plus.addEventListener("click", function() {
        currentCount++;
        count.innerText = currentCount;

        let totalPrice = total * currentCount;
        multi.innerText = totalPrice.toLocaleString() + "원";

        let totalPrice2 = total2 * currentCount;
        multi2.innerText = totalPrice2.toLocaleString() + "원";

        realTotalPrice.innerText = totalPrice.toLocaleString();
    });

    minus.addEventListener("click", function() {
        if (currentCount > 1) {
            currentCount--;
            count.innerText = currentCount;

            let totalPrice = total * currentCount;
            multi.innerText = totalPrice.toLocaleString() + "원";

            let totalPrice2 = total2 * currentCount;
            multi2.innerText = totalPrice2.toLocaleString() + "원";

            realTotalPrice.innerText = totalPrice.toLocaleString();
        }
    });

    
});