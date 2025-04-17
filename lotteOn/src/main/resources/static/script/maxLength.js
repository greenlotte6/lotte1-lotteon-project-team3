document.addEventListener('DOMContentLoaded', function() {
    const input = document.querySelector('.other');

    input.addEventListener('input', function () {
        const maxLen = 25;
        if (input.value.length > maxLen) {
            input.value = input.value.slice(0, maxLen);
        }
    });
});