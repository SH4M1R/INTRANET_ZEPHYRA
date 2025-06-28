package INTRANET_ZEPHYRA.demo.Servicios;

import INTRANET_ZEPHYRA.demo.DAO.RolRepositorio;
import INTRANET_ZEPHYRA.demo.Entidad.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import INTRANET_ZEPHYRA.demo.DAO.UsuarioRepositorio;
import INTRANET_ZEPHYRA.demo.Entidad.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        Set<GrantedAuthority> autoridades = usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre())) // prefijo ROLE_
            .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(),
            usuario.getPassword(),
            usuario.isEnabled(),
            true, true, true,
            autoridades
        );
    }

//    public void  crearUsuario(String username, String password, String nombre) {
//        Usuario usuario = new Usuario();
//        usuario.setUsername(username);
//        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//
//        Rol rol = RolRepositorio.findByNombre(nombre)
//                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
//        usuario.agregarRol(rol);
//        return usuarioRepositorio.save(usuario);
//    }

    public void crearUsuario(Usuario usuario) {
//        Usuario usuario = new Usuario();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Asegúrate que venga sin codificar
//        Rol rol = RolRepositorio.findByNombre(nombre)
//                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
//        usuario.agregarRol(rol);

        usuarioRepositorio.save(usuario);
    }


    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            // Mantener contraseña existente si no fue cambiada
            usuarioRepositorio.findById(usuario.getId()).ifPresent(existing -> usuario.setPassword(existing.getPassword()));
        }
        return usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }


}