package INTRANET_ZEPHYRA.demo.DAO;

import INTRANET_ZEPHYRA.demo.Entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
