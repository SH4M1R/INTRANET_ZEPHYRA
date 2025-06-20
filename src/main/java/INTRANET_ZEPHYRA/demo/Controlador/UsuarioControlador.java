package INTRANET_ZEPHYRA.demo.Controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import INTRANET_ZEPHYRA.demo.Entidad.Rol;
import INTRANET_ZEPHYRA.demo.Entidad.Usuario;
import INTRANET_ZEPHYRA.demo.Servicios.RolServicio;
import INTRANET_ZEPHYRA.demo.Servicios.UsuarioServicio;

@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RolServicio rolServicio;

    // Mostrar lista de usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuario";  // Thymeleaf: listaUsuario.html
    }

    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
    Optional<Usuario> usuarioOpt = usuarioServicio.obtenerUsuarioPorId(id);
    if (usuarioOpt.isPresent()) {
        model.addAttribute("usuario", usuarioOpt.get());
        return "formularioUsuario"; // reutiliza vista del formulario
    } else {
        return "redirect:/usuarios";
    }
}

    // Mostrar formulario para crear usuario nuevo
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioNuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        List<Rol> roles = rolServicio.listarRoles();

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);

        return "nuevoUsuario"; // Thymeleaf: nuevoUsuario.html
    }

    // Procesar formulario para crear nuevo usuario
    @PostMapping("/usuarios/guardar")
public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
    if (usuario.getId() == null) {
        usuarioServicio.crearUsuario(usuario);
    } else {
        usuarioServicio.actualizarUsuario(usuario);
    }
    return "redirect:/usuarios";
}

 public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
         List<Rol> roles = rolServicio.listarRoles();
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles); // Debes tener este método o pasar los roles desde el servicio

        return "formularioUsuario"; // Asegúrate que ese sea el nombre correcto del HTML
    }

@GetMapping("/usuarios/eliminar/{id}")
public String eliminarUsuario(@PathVariable Long id) {
    usuarioServicio.eliminarUsuario(id);
    return "redirect:/usuarios";
}
}
