package INTRANET_ZEPHYRA.demo.Controlador;

import java.util.Arrays;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import INTRANET_ZEPHYRA.demo.Servicios.ProductoServicio;

@Controller
public class ProductoControlador {
    @Autowired
    private ProductoServicio ProductoServicio;

    List<String> listaColor = Arrays.asList("Azul","Negro","Rojo");
    List<String> listaTamaño = Arrays.asList("S","M","L");
    
    @GetMapping("/listaproductos")
public String listaProductos(Model model) {
    model.addAttribute("listaproductos", ProductoServicio.listaProductos());
    return "listaproductos";
}

}