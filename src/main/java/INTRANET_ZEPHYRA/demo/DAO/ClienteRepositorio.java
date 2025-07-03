package INTRANET_ZEPHYRA.demo.DAO;

import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
}