function selectOne(element) {
    const payment = document.getElementsByClassName("payment");
    Array.from(payment).forEach((cb) => {
        cb.checked = false;
    });
    element.checked = true;
}