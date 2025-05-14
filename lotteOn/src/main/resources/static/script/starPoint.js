document.addEventListener('DOMContentLoaded', function () {
   const startPoint = document.querySelector('.starPoint');
   const point = parseInt(startPoint.innerText);

   startPoint.innerText = '⭐'.repeat(point);
});