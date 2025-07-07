package INTRANET_ZEPHYRA.demo.Controlador;

import INTRANET_ZEPHYRA.demo.DAO.ProductoDAO;
import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVentaWrapper;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;
import INTRANET_ZEPHYRA.demo.Servicios.VentaServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VentaControlador {

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private VentaServicio ventaServicio;

    // Mostrar formulario de ventas
    @GetMapping("/Ventas")
    public String nuevaVenta(Model model) {
        model.addAttribute("productos", productoDAO.findAll());
        model.addAttribute("detalles", new DetalleVentaWrapper());
        return "Ventas";
    }

    // Registrar venta
    @PostMapping("/registrar")
    public String registrarVenta(
            @ModelAttribute Venta venta,
            @RequestParam(name = "nombreCliente", required = false) String nombreCliente,
            @RequestParam(name = "dniCliente", required = false) String documentoCliente,
            @RequestParam(name = "correoCliente", required = false) String correoCliente,
            @RequestParam(name = "metodoPago", required = false) String metodoPago,
            @RequestParam(name = "montoPagado", required = false) Double montoPagado,
            @RequestParam(name = "vuelto", required = false) Double vuelto,
            @RequestParam(name = "codigoOperacion", required = false) String codigoOperacion,
            @RequestParam(name = "numeroTarjeta", required = false) String numeroTarjeta,
            @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        // Crear cliente temporal con datos del formulario
        Cliente cliente = new Cliente();
        cliente.setNombre(nombreCliente);
        cliente.setDocumento(documentoCliente);
        cliente.setCorreo(correoCliente);

        // Registrar la venta pasando todos los datos necesarios
        ventaServicio.registrarVenta(
                venta,
                username,
                cliente,
                metodoPago,
                montoPagado,
                codigoOperacion,
                numeroTarjeta
        );

        return "redirect:/Ventas?exito";
    }

    // Mostrar lista de ventas registradas
    @GetMapping("/GestionVentas")
    public String mostrarVentas(Model model) {
        List<Venta> listaVentas = ventaServicio.obtenerTodasLasVentas();
        model.addAttribute("listaVentas", listaVentas);
        return "GestionVentas";
    }

    @GetMapping("/api/ventas/{id}/detalle")
public String obtenerDetalleVenta(@PathVariable Long id, Model model) {
    Venta venta = ventaServicio.obtenerVentaPorId(id);
    model.addAttribute("venta", venta);
    return "fragmentos/detalle_venta :: detalleContenido";
}
}
