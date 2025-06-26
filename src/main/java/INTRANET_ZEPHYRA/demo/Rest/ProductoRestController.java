package INTRANET_ZEPHYRA.demo.Rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import INTRANET_ZEPHYRA.demo.DAO.ProductoDAO;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    @Autowired
    private ProductoDAO productoDAO;

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return productoDAO.findByNombreContainingIgnoreCase(nombre);
    }
}