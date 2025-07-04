
document.addEventListener('DOMContentLoaded', function() {


        // Gráfica combinada: Barras y Línea de ventas por mes
    const ctxCombo = document.getElementById('chart-ventas-combinada').getContext('2d');
    new Chart(ctxCombo, {
        type: 'bar',
        data: {
            labels: labelsMeses,
            datasets: [
                {
                    type: 'bar',
                    label: 'Ventas (Barras)',
                    data: ventasPorMes,
                    backgroundColor: 'rgba(13, 110, 253, 0.7)',
                    borderColor: 'rgba(13, 110, 253, 1)',
                    borderWidth: 1
                },
                {
                    type: 'line',
                    label: 'Ventas (Línea)',
                    data: ventasPorMes,
                    fill: false,
                    borderColor: 'rgba(220, 53, 69, 0.8)',
                    backgroundColor: 'rgba(220, 53, 69, 0.3)',
                    tension: 0.3,
                    pointBackgroundColor: 'rgba(220, 53, 69, 1)',
                    pointRadius: 4
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: true },
                title: { display: false }
            },
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

const ctxIngresos = document.getElementById('chart-ingresos-mensuales').getContext('2d');
new Chart(ctxIngresos, {
    type: 'bar',
    data: {
        labels: labelsMeses,
        datasets: [{
            label: 'Total de ventas (S/)',
            data: totalVentaPorMes,
            backgroundColor: 'rgba(40, 167, 69, 0.7)',
            borderColor: 'rgba(40, 167, 69, 1)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: true },
            title: { display: false }
        },
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function(value) {
                        return 'S/ ' + value.toFixed(2);
                    }
                }
            }
        }
    }
});
});
