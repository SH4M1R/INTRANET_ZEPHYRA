package INTRANET_ZEPHYRA.demo.Controlador;

import INTRANET_ZEPHYRA.demo.Entidad.Proveedor;
import INTRANET_ZEPHYRA.demo.Servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProveedorControlador {
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping({"/proveedores", "/GestionProveedores"})
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorServicio.listarProveedores());
        return "GestionProveedores";
    }

    @GetMapping("/proveedores/nuevo")
    public String nuevoProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "nuevoProveedor";
    }

    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(@ModelAttribute Proveedor proveedor) {
        proveedorServicio.guardarProveedor(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/proveedores/editar/{idProveedor}")
    public String editarProveedor(@PathVariable Long idProveedor, Model model) {
        Proveedor proveedor = proveedorServicio.obtenerProveedorPorId(idProveedor)
                .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido: " + idProveedor));
        model.addAttribute("proveedor", proveedor);
        return "editarProveedor";
    }

    @GetMapping("/proveedores/eliminar/{idProveedor}")
    public String eliminarProveedor(@PathVariable Long idProveedor) {
        proveedorServicio.eliminarProveedor(idProveedor);
        return "redirect:/proveedores";
    }
}
