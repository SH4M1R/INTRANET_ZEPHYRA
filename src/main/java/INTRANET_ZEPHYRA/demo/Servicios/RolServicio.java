package INTRANET_ZEPHYRA.demo.Servicios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import INTRANET_ZEPHYRA.demo.DAO.RolRepositorio;
import INTRANET_ZEPHYRA.demo.Entidad.Rol;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public List<Rol> listarRoles() {
        return rolRepositorio.findAll();
    }

    public Rol obtenerRolPorId(Long id) {
        return rolRepositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));
    }
}