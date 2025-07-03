package INTRANET_ZEPHYRA.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import INTRANET_ZEPHYRA.demo.Entidad.Venta;

@Repository
public interface VentaDAO extends JpaRepository<Venta, Long> {
}