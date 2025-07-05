document.addEventListener("DOMContentLoaded", function () {
    const buscador = document.getElementById("buscador");
    const tablaBusquedaProductos = document.getElementById("tablaBusquedaProductos");
    const tablaBusquedaContainer = document.getElementById("tablaBusquedaContainer");
    const btnCerrarTabla = document.getElementById("btnCerrarTabla");

    buscador.addEventListener("input", function () {
        const query = buscador.value.trim();

        if (query.length >= 2) {
            fetch(`/api/productos/buscar?nombre=${query}`)
                .then(response => response.json())
                .then(data => {
                    tablaBusquedaProductos.innerHTML = "";
                    tablaBusquedaContainer.style.display = data.length ? "block" : "none";

                    data.forEach(producto => {
                        const fila = document.createElement("tr");
                        fila.innerHTML = `
                            <td>${producto.nombre}</td>
                            <td>${producto.color}</td>
                            <td>${producto.tamaño}</td>
                            <td>S/ ${producto.precio.toFixed(2)}</td>
                            <td>${producto.stock}</td>
                            <td>
                                <button type="button" class="btn btn-success btn-sm" onclick='agregarProducto(${JSON.stringify(producto)})'><i class="bi bi-file-plus"></i></button>
                            </td>
                        `;
                        tablaBusquedaProductos.appendChild(fila);
                    });
                });
        } else {
            tablaBusquedaContainer.style.display = "none";
        }
    });

    btnCerrarTabla.addEventListener("click", () => {
        tablaBusquedaContainer.style.display = "none";
        buscador.value = "";
    });

    document.getElementById("metodoPago").addEventListener("change", cambiarMetodoPago);
    document.getElementById("montoPagado").addEventListener("input", calcularVuelto);

    const dniInput = document.getElementById("dniCliente");
    dniInput.addEventListener("input", function () {
        this.value = this.value.replace(/\D/g, "").slice(0, 8);
    });

    document.querySelectorAll(".izipay-input").forEach(input => {
        input.addEventListener("input", function () {
            this.value = this.value.replace(/\D/g, "").slice(0, 4);
        });
    });

    const formVenta = document.querySelector("form");
    formVenta.addEventListener("submit", function () {
        document.getElementById("inputNombreCliente").value = document.getElementById("nombreCliente").value.trim();
        document.getElementById("inputDniCliente").value = document.getElementById("dniCliente").value.trim();
        document.getElementById("inputCorreoCliente").value = document.getElementById("correoCliente").value.trim();
    });
});

function agregarProducto(producto) {
    const tabla = document.getElementById("detalleVentaBody");
    const fila = document.createElement("tr");
    fila.dataset.idProducto = producto.idProducto;

    fila.innerHTML = `
        <td>${producto.nombre}</td>
        <td>S/ ${producto.precio.toFixed(2)}</td>
        <td><input type="number" value="1" class="form-control cantidad" min="1" max="${producto.stock}" onchange="actualizarSubtotal(this)"></td>
        <td class="subtotal">S/ ${producto.precio.toFixed(2)}</td>
        <td><button type="button" class="btn btn-danger btn-sm" onclick="eliminarFila(this)"><i class="bi bi-trash3 me-1"></i></button></td>
    `;

    tabla.appendChild(fila);
    actualizarTotal();
}

function actualizarSubtotal(input) {
    const fila = input.closest("tr");
    const precio = parseFloat(fila.children[1].innerText.replace("S/ ", ""));
    const cantidad = parseInt(input.value);
    const subtotal = cantidad * precio;
    fila.querySelector(".subtotal").textContent = "S/ " + subtotal.toFixed(2);
    actualizarTotal();
}

function eliminarFila(btn) {
    btn.closest("tr").remove();
    actualizarTotal();
}

function actualizarTotal() {
    const filas = document.querySelectorAll("#detalleVentaBody tr");
    let total = 0;
    filas.forEach(fila => {
        const subtotalTexto = fila.querySelector(".subtotal").innerText.replace("S/ ", "");
        total += parseFloat(subtotalTexto);
    });
    document.getElementById("total").textContent = "S/ " + total.toFixed(2);
    document.getElementById("inputTotalVenta").value = total.toFixed(2);
    calcularVuelto();
}

function cambiarMetodoPago() {
    document.getElementById("pagoEfectivo").style.display = "none";
    document.getElementById("pagoYape").style.display = "none";
    document.getElementById("pagoIzipay").style.display = "none";

    const metodo = document.getElementById("metodoPago").value;
    if (metodo === "efectivo") document.getElementById("pagoEfectivo").style.display = "block";
    if (metodo === "yape") document.getElementById("pagoYape").style.display = "block";
    if (metodo === "izipay") document.getElementById("pagoIzipay").style.display = "block";
}

function calcularVuelto() {
    const monto = parseFloat(document.getElementById("montoPagado").value) || 0;
    const total = parseFloat(document.getElementById("total").textContent.replace("S/ ", "")) || 0;
    const vuelto = monto - total;
    const vueltoTexto = document.getElementById("vueltoTexto");

    if (vueltoTexto) {
        if (vuelto < 0) {
            vueltoTexto.textContent = "Monto insuficiente";
            vueltoTexto.style.color = "red";
        } else {
            vueltoTexto.textContent = `S/ ${vuelto.toFixed(2)}`;
            vueltoTexto.style.color = "green";
        }
    }
}

function eliminarTodo() {
    document.getElementById("detalleVentaBody").innerHTML = "";
    actualizarTotal();
}

function imprimirVoucher() {
    window.print();
}