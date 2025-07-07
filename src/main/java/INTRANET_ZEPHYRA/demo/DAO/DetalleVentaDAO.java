package INTRANET_ZEPHYRA.demo.DAO;

import INTRANET_ZEPHYRA.demo.Entidad.Venta;
import INTRANET_ZEPHYRA.demo.Entidad.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaDAO extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVenta(Venta venta);
}