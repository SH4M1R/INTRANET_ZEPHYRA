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
        System.out.println("usuarioServicio: " + usuarioServicio);
        System.out.println("proveedorServicio: " + proveedorServicio);
        System.out.println("productoServicio: " + productoServicio);
        System.out.println("ventaServicio: " + ventaServicio);

        model.addAttribute("totalUsuarios", usuarioServicio.listarUsuarios().size());
        model.addAttribute("totalProveedores", proveedorServicio.listarProveedores().size());
        model.addAttribute("totalProductos", productoServicio.listaProductos().size());
        model.addAttribute("totalVentas", ventaServicio.obtenerTotalVentas());

        List<String> labelsMeses = ventaServicio.obtenerMesesVentas();
        List<Integer> ventasPorMes = ventaServicio.obtenerVentasPorMes();
        model.addAttribute("labelsMeses", labelsMeses);
        model.addAttribute("ventasPorMes", ventasPorMes);
        return "inicio";
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
}
}
