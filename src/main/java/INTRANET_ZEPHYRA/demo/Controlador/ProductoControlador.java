package INTRANET_ZEPHYRA.demo.Controlador;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

@GetMapping("/producto/editar/{idProducto}")
    public String actualizarProducto(@PathVariable Integer idProducto, Model model) {
        Producto producto = ProductoServicio.obtenerProducto(idProducto);
        model.addAttribute("producto", producto);
        return "editarProducto";  // Nombre de la vista para editar
    }

    // --- NUEVO: Método para actualizar el producto ---
    @PostMapping("/producto/actualizar")
    public String actualizarProducto(@ModelAttribute("producto") Producto producto) {
        // Aquí guardamos el producto actualizado
        ProductoServicio.guardarProducto(producto);
        return "redirect:/listaproductos";
    }

    // --- NUEVO: Método para eliminar producto ---
    @GetMapping("/producto/eliminar/{idProducto}")
    public String eliminarProducto(@PathVariable Integer idProducto) {
        ProductoServicio.eliminarProducto(idProducto);
        return "redirect:/listaproductos";
    }
}
