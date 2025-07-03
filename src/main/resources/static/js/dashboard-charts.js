
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

    // Para datos reales: puedes usar Thymeleaf para inyectar los datos en variables JS,
    // o hacer una petición AJAX a un endpoint del backend que devuelva ventas por mes.
});
