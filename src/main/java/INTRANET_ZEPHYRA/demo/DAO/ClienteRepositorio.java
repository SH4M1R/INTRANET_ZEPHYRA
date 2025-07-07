package INTRANET_ZEPHYRA.demo.DAO;

import INTRANET_ZEPHYRA.demo.Entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    Optional<Cliente> findByDocumento(String documento);
    Optional<Cliente> findByNombre(String nombre);
}