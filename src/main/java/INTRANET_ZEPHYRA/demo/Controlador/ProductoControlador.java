package INTRANET_ZEPHYRA.demo.Controlador;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;
import INTRANET_ZEPHYRA.demo.Servicios.ProductoServicio;

@Controller
public class ProductoControlador {
    @Autowired
    private ProductoServicio ProductoServicio;
    @GetMapping("/listaproductos")
public String listaProductos(Model model) {
    model.addAttribute("listaproductos", ProductoServicio.listaProductos());
    return "listaproductos";
}

    @GetMapping("/nuevoProducto")
public String nuevoProducto(Model model) {
    model.addAttribute("producto", new Producto());
    return "nuevoProducto";
}

@PostMapping("/guardarProducto")
public String guardarProducto(@ModelAttribute("producto") Producto producto) {
    ProductoServicio.guardarProducto(producto);
    return "redirect:/listaproductos";
}
}
