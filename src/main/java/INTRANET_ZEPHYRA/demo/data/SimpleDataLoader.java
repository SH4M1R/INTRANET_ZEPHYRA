package INTRANET_ZEPHYRA.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import INTRANET_ZEPHYRA.demo.DAO.UsuarioRepositorio;
import INTRANET_ZEPHYRA.demo.Entidad.Usuario;
import jakarta.annotation.PostConstruct;

@Component
public class SimpleDataLoader {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        if (usuarioRepositorio.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEnabled(true);
            usuarioRepositorio.save(admin);
            System.out.println("Usuario admin creado.");
        }
    }
}