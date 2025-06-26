package INTRANET_ZEPHYRA.demo.DAO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;

public interface ProductoDAO extends JpaRepository<Producto, Integer>{
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}