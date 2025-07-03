package INTRANET_ZEPHYRA.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVenta;

public interface DetalleVentaDAO extends JpaRepository<DetalleVenta,Integer>{

}