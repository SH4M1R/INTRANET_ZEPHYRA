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

    // LISTAR USUARIOS
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "listaUsuario";
    }

    // FORMULARIO NUEVO USUARIO
    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        List<Rol> roles = rolServicio.listarRoles(); // Obtener todos los roles disponibles

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);

        return "nuevoUsuario";
    }

    // GUARDAR NUEVO O ACTUALIZADO
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

    // FORMULARIO DE EDICIÓN
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de usuario inválido: " + id));

        List<Rol> roles = rolServicio.listarRoles();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);
        return "editarUsuario";
    }

    // ELIMINAR USUARIO
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}