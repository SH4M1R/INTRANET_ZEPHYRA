package INTRANET_ZEPHYRA.demo.Servicios;

import INTRANET_ZEPHYRA.demo.Entidad.Proveedor;
import INTRANET_ZEPHYRA.demo.DAO.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServicio {
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    public List<Proveedor> listarProveedores() {
        return proveedorRepositorio.findAll();
    }

    public void guardarProveedor(Proveedor proveedor) {
        proveedorRepositorio.save(proveedor);
    }

    public Optional<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepositorio.findById(id);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepositorio.deleteById(id);
    }
}
