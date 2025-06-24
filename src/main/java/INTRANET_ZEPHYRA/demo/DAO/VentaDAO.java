package INTRANET_ZEPHYRA.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import INTRANET_ZEPHYRA.demo.Entidad.Venta;

public interface VentaDAO extends JpaRepository<Venta, Long> {
}