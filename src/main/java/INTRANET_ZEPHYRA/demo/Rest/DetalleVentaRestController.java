package INTRANET_ZEPHYRA.demo.Rest;

import INTRANET_ZEPHYRA.demo.DAO.VentaDAO;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVenta;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/detalle") // NUEVA BASE DE RUTA para evitar conflicto
public class DetalleVentaRestController {

    @Autowired
    private VentaDAO ventaDAO;

    @GetMapping("/venta/{id}")
    public String obtenerDetalleVenta(@PathVariable Long id, Model model) {
        Venta venta = ventaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        List<DetalleVenta> detalles = venta.getDetalles();
        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);
        return "fragmentos/detalleVenta :: detalleVentaModal";
    }
}