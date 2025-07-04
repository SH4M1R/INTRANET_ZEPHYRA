package INTRANET_ZEPHYRA.demo.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;
import INTRANET_ZEPHYRA.demo.Servicios.UsuarioServicio;
import INTRANET_ZEPHYRA.demo.Servicios.ProductoServicio;
import INTRANET_ZEPHYRA.demo.Servicios.VentaServicio;
import INTRANET_ZEPHYRA.demo.Servicios.ProveedorServicio;

@Controller
public class InicioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private VentaServicio ventaServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping({"/", "/inicio"})
    public String dashboard(Model model) {
        try {
            // Datos numéricos
            model.addAttribute("totalUsuarios", usuarioServicio.listarUsuarios().size());
            model.addAttribute("totalProveedores", proveedorServicio.listarProveedores().size());
            model.addAttribute("totalProductos", productoServicio.listaProductos().size());
            model.addAttribute("totalVentas", ventaServicio.obtenerTotalVentas());

            // Datos para el gráfico de cantidad de ventas por mes
            List<String> labelsMeses = ventaServicio.obtenerMesesVentas(); // Ej: ["ENE", "FEB", "MAR"]
            List<Integer> ventasPorMes = ventaServicio.obtenerVentasPorMes(); // Ej: [10, 15, 8]
            List<Double> totalVentaPorMes = ventaServicio.obtenerTotalVentaPorMes(); // Ej: [2500.0, 3400.0, 1800.0]

            model.addAttribute("labelsMeses", labelsMeses);
            model.addAttribute("ventasPorMes", ventasPorMes);
            model.addAttribute("totalVentaPorMes", totalVentaPorMes);

            return "inicio";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
