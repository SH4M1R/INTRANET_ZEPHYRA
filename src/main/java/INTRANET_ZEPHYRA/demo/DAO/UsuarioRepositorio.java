package INTRANET_ZEPHYRA.demo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import INTRANET_ZEPHYRA.demo.Entidad.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
        Optional<Usuario> findByUsername(String username);

}