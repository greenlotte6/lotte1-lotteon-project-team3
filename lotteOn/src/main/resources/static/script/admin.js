document.addEventListener('DOMContentLoaded', () => {
    const ctx = document.getElementById('orderChart').getContext('2d');
    const pie = document.getElementById('salesChart').getContext('2d');
    const orderChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['10-10', '10-11', '10-12', '10-13', '10-14'],
            datasets: [
                {
                    label: '주문',
                    data: [10, 15, 9, 12, 13],
                    backgroundColor: 'rgba(54, 162, 235, 0.7)', // 파랑
                },
                {
                    label: '결제',
                    data: [6, 12, 5, 7, 8],
                    backgroundColor: 'rgba(255, 99, 132, 0.7)', // 빨강
                },
                {
                    label: '취소',
                    data: [3, 5, 8, 3, 2],
                    backgroundColor: 'rgba(75, 192, 192, 0.7)', // 연두
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            size: 14
                        }
                    }
                },
                title: {
                    display: true,
                    text: '일자별 주문/결제/취소'
                }
            },
            scales: {
                x: {
                    stacked: false
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    const salesChart = new Chart(pie, {
        type: 'pie',
        data: {
            labels: ['가전', '식품', '의류', '기타'],
            datasets: [{
                label: '주요매출',
                data: [50, 25, 15, 10], // 매출 비율
                backgroundColor: [
                    'rgba(54, 162, 235, 0.7)',   // 가전 - 파랑
                    'rgba(255, 99, 132, 0.7)',   // 식품 - 빨강
                    'rgba(154, 205, 50, 0.7)',   // 의류 - 연두
                    'rgba(153, 102, 255, 0.7)'   // 기타 - 보라
                ],
                borderColor: '#fff',
                borderWidth: 2
            }]
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: '주요매출',
                    font: {
                        size: 18
                    }
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 14
                        }
                    }
                }
            }
        }
    });
});