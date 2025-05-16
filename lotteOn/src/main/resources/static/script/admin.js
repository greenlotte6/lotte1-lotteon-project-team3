document.addEventListener('DOMContentLoaded', () => {
    // 막대차트: 동적 데이터 로딩
    fetch('/admin/chart/orders')
        .then(res => res.json())
        .then(data => {
            const ctx = document.getElementById('orderChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data.labels,
                    datasets: [
                        {
                            label: '주문',
                            data: data.orders,
                            backgroundColor: 'rgba(54, 162, 235, 0.7)', // 파랑
                        },
                        {
                            label: '구매확정',
                            data: data.confirmed,
                            backgroundColor: 'rgba(255, 206, 86, 0.7)', // 노랑
                        },
                        {
                            label: '반품/취소',
                            data: data.refunds,
                            backgroundColor: 'rgba(255, 99, 132, 0.7)', // 빨강
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
                            text: '일자별 주문/구매확정/반품취소'
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
        });

    // 원형 차트는 그대로 유지
    fetch('/admin/chart/sales')
        .then(res => res.json())
        .then(data => {
            const pie = document.getElementById('salesChart').getContext('2d');
            const salesChart = new Chart(pie, {
                type: 'pie',
                data: {
                    labels: data.labels, // ← 서버에서 받아온 ["패션", "라이프", "식품", "기타"]
                    datasets: [{
                        label: '카테고리별 매출',
                        data: data.sales, // ← 서버에서 받아온 [150000, 80000, 120000, 50000]
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.7)',   // 패션
                            'rgba(255, 206, 86, 0.7)',   // 라이프
                            'rgba(75, 192, 192, 0.7)',   // 식품
                            'rgba(153, 102, 255, 0.7)'   // 기타
                        ],
                        borderColor: '#fff',
                        borderWidth: 2
                    }]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: '카테고리별 매출',
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

});
