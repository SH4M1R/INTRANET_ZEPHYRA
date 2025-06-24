let productosAgregados = new Set();

function agregarProductoSeleccionado() {
    const select = document.getElementById("productoSelect");
    const selectedOption = select.options[select.selectedIndex];
    const id = selectedOption.value;
    const nombre = selectedOption.getAttribute("data-nombre");
    const precio = parseFloat(selectedOption.getAttribute("data-precio")).toFixed(2);
    const stock = selectedOption.getAttribute("data-stock");

    if (!id || productosAgregados.has(id)) {
        alert("Producto ya agregado o inválido.");
        return;
    }

    productosAgregados.add(id);

    const tableBody = document.getElementById("detalleVentaBody");
    const row = document.createElement("tr");

    row.innerHTML = `
        <td>
            ${nombre}
            <input type="hidden" name="detalles.detalles[][producto].idProducto" value="${id}" />
        </td>
        <td>
            <input type="number" class="form-control" value="${precio}" readonly 
                   name="detalles.detalles[][precioUnitario]" />
        </td>
        <td>
            <input type="number" class="form-control" name="detalles.detalles[][cantidad]" 
                   value="1" min="1" max="${stock}" onchange="actualizarSubtotal(this)" />
        </td>
        <td class="subtotal">S/ ${precio}</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="eliminarFila(this, '${id}')">Eliminar</button>
        </td>
    `;

    tableBody.appendChild(row);
    actualizarTotal();
}

function actualizarSubtotal(input) {
    const row = input.closest("tr");
    const precio = parseFloat(row.querySelector('[name$="precioUnitario"]').value);
    const cantidad = parseInt(input.value);
    const subtotal = precio * cantidad;
    row.querySelector(".subtotal").innerText = `S/ ${subtotal.toFixed(2)}`;
    actualizarTotal();
}

function actualizarTotal() {
    let total = 0;
    document.querySelectorAll("#detalleVentaBody tr").forEach(row => {
        const cantidad = parseInt(row.querySelector('[name$="cantidad"]').value);
        const precio = parseFloat(row.querySelector('[name$="precioUnitario"]').value);
        total += cantidad * precio;
    });
    document.getElementById("total").innerText = `S/ ${total.toFixed(2)}`;
}

function eliminarFila(button, idProducto) {
    const row = button.closest("tr");
    row.remove();
    productosAgregados.delete(idProducto);
    actualizarTotal();
}
