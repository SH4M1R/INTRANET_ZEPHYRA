package INTRANET_ZEPHYRA.demo.Controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import INTRANET_ZEPHYRA.demo.DAO.ProductoDAO;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVentaWrapper;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;
import INTRANET_ZEPHYRA.demo.Servicios.VentaServicio;

@Controller
public class VentaControlador {

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private VentaServicio ventaServicio;

    @GetMapping("/Ventas")
    public String nuevaVenta(Model model) {
    model.addAttribute("productos", productoDAO.findAll());
    model.addAttribute("detalles", new DetalleVentaWrapper());
        return "Ventas";
}

    @PostMapping("/registrar")
public String registrarVenta(@ModelAttribute Venta venta,
                             @AuthenticationPrincipal UserDetails userDetails) {
    String usuario = userDetails.getUsername();
    ventaServicio.registrarVenta(venta, usuario);
    return "redirect:/Ventas?exito";
}

@GetMapping("/GestionVentas")
    public String mostrarVentas(Model model) {
        List<Venta> listaVentas = ventaServicio.obtenerTodasLasVentas();
        model.addAttribute("listaVentas", listaVentas);
        return "GestionVentas";
    }
}