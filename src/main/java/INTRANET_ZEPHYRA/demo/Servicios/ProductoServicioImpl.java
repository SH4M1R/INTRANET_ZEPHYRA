package INTRANET_ZEPHYRA.demo.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import INTRANET_ZEPHYRA.demo.Entidad.Producto;

@Service
public class ProductoServicioImpl implements ProductoServicio{
    @Autowired
    private INTRANET_ZEPHYRA.demo.DAO.ProductoDAO ProductoDAO;
    @Override
    public List<Producto> listaProductos(){
      return ProductoDAO.findAll();
    }

    @Override
    public Producto guardarProducto(Producto Producto) { return ProductoDAO.save(Producto);}

    @Override
    public Producto obtenerProducto(Integer idProducto) { return ProductoDAO.findById(idProducto).get();}

    @Override
    public Producto actualizarProducto(Producto Producto) { return ProductoDAO.save(Producto);}

    @Override
    public void eliminarProducto(Integer idProducto) { ProductoDAO.deleteById(idProducto);}

    public List<Producto> buscarPorNombre(String nombre) {
        return ProductoDAO.findByNombreContainingIgnoreCase(nombre);
    }
}
