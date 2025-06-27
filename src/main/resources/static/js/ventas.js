document.addEventListener("DOMContentLoaded", function () {
    const buscador = document.getElementById("buscador");
    const resultado = document.getElementById("resultado-busqueda");
    const tablaBusquedaContainer = document.getElementById("tablaBusquedaContainer");
    const tablaBusquedaProductos = document.getElementById("tablaBusquedaProductos");

    buscador.addEventListener("input", function () {
        const query = buscador.value.trim();

        if (query.length >= 2) {
            fetch(`/api/productos/buscar?nombre=${query}`)
                .then(response => response.json())
                .then(data => {
                    tablaBusquedaProductos.innerHTML = "";
                    if (data.length > 0) {
                        tablaBusquedaContainer.style.display = "block";
                        data.forEach(producto => {
                            const fila = document.createElement("tr");
                            fila.innerHTML = `
                                <td>${producto.nombre}</td>
                                <td>${producto.color}</td>
                                <td>${producto.tamaño}</td>
                                <td>S/ ${producto.precio.toFixed(2)}</td>
                                <td>${producto.stock}</td>
                                <td><button class="btn btn-success btn-sm" onclick='agregarProducto(${JSON.stringify(producto)})'>Agregar</button></td>
                            `;
                            tablaBusquedaProductos.appendChild(fila);
                        });
                    } else {
                        tablaBusquedaContainer.style.display = "none";
                    }
                });
        } else {
            tablaBusquedaContainer.style.display = "none";
        }
    });
});

function agregarProducto(producto) {
    const tabla = document.getElementById("detalleVentaBody");
    const fila = document.createElement("tr");

    fila.innerHTML = `
        <td>${producto.nombre}</td>
        <td><input type="hidden" name="idProducto" value="${producto.idProducto}">S/ ${producto.precio.toFixed(2)}</td>
        <td><input type="number" name="cantidad" value="1" class="form-control form-control-sm" min="1" max="${producto.stock}" onchange="actualizarSubtotal(this)"></td>
        <td class="subtotal">S/ ${producto.precio.toFixed(2)}</td>
        <td><button class="btn btn-danger btn-sm" onclick="eliminarFila(this)">Eliminar</button></td>
    `;

    tabla.appendChild(fila);
    actualizarTotal();
}

function actualizarSubtotal(input) {
    const fila = input.closest("tr");
    const precioTexto = fila.children[1].innerText.replace("S/ ", "").trim();
    const cantidad = parseInt(input.value);
    const precio = parseFloat(precioTexto);
    const subtotal = cantidad * precio;
    fila.querySelector(".subtotal").textContent = "S/ " + subtotal.toFixed(2);
    actualizarTotal();
}

function eliminarFila(btn) {
    const fila = btn.closest("tr");
    fila.remove();
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
}

function cambiarMetodoPago() {
    const metodo = document.getElementById("metodoPago").value;

    // Ocultar todos
    document.getElementById("pagoEfectivo").style.display = "none";
    document.getElementById("pagoTarjeta").style.display = "none";
    document.getElementById("pagoYape").style.display = "none";

    // Mostrar según la opción
    if (metodo === "efectivo") {
        document.getElementById("pagoEfectivo").style.display = "block";
    } else if (metodo === "tarjeta") {
        document.getElementById("pagoTarjeta").style.display = "block";
    } else if (metodo === "yape") {
        document.getElementById("pagoYape").style.display = "block";
    }
}

function calcularVuelto() {
    const montoPagado = parseFloat(document.getElementById("montoPagado").value) || 0;
    const totalTexto = document.getElementById("total").innerText.replace("S/", "").trim();
    const total = parseFloat(totalTexto) || 0;

    const vuelto = montoPagado - total;
    const vueltoTexto = document.getElementById("vueltoTexto");

    if (vuelto < 0) {
        vueltoTexto.innerText = "Monto insuficiente";
        vueltoTexto.style.color = "red";
    } else {
        vueltoTexto.innerText = `S/ ${vuelto.toFixed(2)}`;
        vueltoTexto.style.color = "green";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const numeroTarjeta = document.getElementById("numeroTarjeta");

    if (numeroTarjeta) {
        numeroTarjeta.addEventListener("input", function (e) {
            this.value = this.value.replace(/[^0-9\-]/g, "");
        });
    }

    // También conecta evento para método de pago
    const metodo = document.getElementById("metodoPago");
    if (metodo) {
        metodo.addEventListener("change", cambiarMetodoPago);
    }
});

document.querySelector("form").addEventListener("submit", function(e) {
    const metodo = document.getElementById("metodoPago").value;
    if (metodo === "efectivo") {
        const montoPagado = parseFloat(document.getElementById("montoPagado").value) || 0;
        const totalTexto = document.getElementById("total").innerText.replace("S/", "").trim();
        const total = parseFloat(totalTexto) || 0;

        if (montoPagado < total) {
            alert("El monto recibido es menor al total. No se puede realizar la venta.");
            e.preventDefault(); // Cancela envío del formulario
        }
    }
});