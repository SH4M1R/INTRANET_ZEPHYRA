package INTRANET_ZEPHYRA.demo.Controlador;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "listaUsuario";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolServicio.listarRoles());
        return "nuevoUsuario";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                 @RequestParam("rolId") Long rolId) {
        Rol rol = rolServicio.obtenerRolPorId(rolId);
        usuario.setRoles(Set.of(rol));

        if (usuario.getId() == null) {
            usuarioServicio.crearUsuario(usuario);
        } else {
            usuarioServicio.actualizarUsuario(usuario);
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
public String editarUsuario(@PathVariable Long id, Model model) {
    Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));

    System.out.println("Usuario a editar: " + usuario.getUsername());
    System.out.println("Roles actuales: " + usuario.getRoles());

    List<Rol> roles = rolServicio.listarRoles();
    model.addAttribute("usuario", usuario);
    model.addAttribute("roles", roles);

    return "editarUsuario";
}


    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}