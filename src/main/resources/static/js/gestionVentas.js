function verDetalle(btn) {
    const id = btn.getAttribute('data-id');
    const modalBody = document.getElementById('detalleVentaContenido');

    // Mostrar loader mientras carga
    modalBody.innerHTML = `
        <div class="text-center text-muted">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
            </div>
            <p class="mt-2">Cargando detalles de la venta...</p>
        </div>
    `;

    // Usamos la nueva ruta actualizada
    fetch(`/detalle/venta/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("No se pudo cargar el detalle");
            return res.text();
        })
        .then(html => {
            modalBody.innerHTML = html;
            new bootstrap.Modal(document.getElementById('modalDetalleVenta')).show();
        })
        .catch(err => {
            modalBody.innerHTML = `<div class="alert alert-danger">Error al cargar detalles.</div>`;
        });
}